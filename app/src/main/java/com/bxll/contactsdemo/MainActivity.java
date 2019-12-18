package com.bxll.contactsdemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bxll.contactsdemo.ItemDecoration.IndexerDecoration;
import com.bxll.contactsdemo.ItemDecoration.StickyHeaderDecoration;
import com.bxll.contactsdemo.adapter.ContactsAdapter;
import com.bxll.contactsdemo.fragment.RationaleDialogFragment;
import com.github.promeg.pinyinhelper.Pinyin;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, RationaleDialogFragment.Callback {
    private static final String READ_CONTACTS_PERMISSION = Manifest.permission.READ_CONTACTS;
    private static final int LOADER_ID_CONTACTS = 0x01;
    private static final int REQUEST_CODE_READ_CONTACTS = 0x10;
    private RecyclerView mContactsList;
    private ContactsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initSupportActionBar();
        initSearchView();
        initFAB();
        initRecyclerView();
        checkAndRequestPermission();
    }

    private void initRecyclerView() {
        mContactsList = findViewById(R.id.contact_list);
        mContactsList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ContactsAdapter(null, android.R.layout.simple_list_item_1,
                new String[]{ContactsQuery.NAME}, new int[]{android.R.id.text1});
        mAdapter.setSortColumnName(ContactsQuery.SORT_KEY);
        mContactsList.setAdapter(mAdapter);
        StickyHeaderDecoration stickyHeaderDecoration = new StickyHeaderDecoration.Builder(this,
                StickyHeaderDecoration.VERTICAL, mAdapter)
                .create();
        mContactsList.addItemDecoration(stickyHeaderDecoration);
        mContactsList.addItemDecoration(new IndexerDecoration(this));
    }

    private void checkAndRequestPermission() {
        if (ContextCompat.checkSelfPermission(this, READ_CONTACTS_PERMISSION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, READ_CONTACTS_PERMISSION)) {
                RationaleDialogFragment
                        .newInstance("请求联系人权限", "应用需要联系人权限，以便能读取联系人并显示出来。")
                        .show(getSupportFragmentManager(), "rationale_fragment");
            } else {
                requestReadContactsPermission();
            }
        } else {
            initContactsLoader();
        }
    }

    private void initSupportActionBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            // 设置home图标
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setHomeAsUpIndicator(R.drawable.ic_home_white_24dp);
        }
    }

    private void initSearchView() {
        SearchView searchView = findViewById(R.id.contacts_search_view);
        // 默认展开状态
        searchView.onActionViewExpanded();
        // 搜索图标左边显示
        searchView.setIconifiedByDefault(false);
        // 去掉下划线
        searchView.findViewById(R.id.search_plate).setBackground(null);
        // 设置圆角背景
        searchView.setBackground(getDrawable(R.drawable.search_view_bg));
        // 设置提示字符
        searchView.setQueryHint(getString(R.string.contact_search_hint));

        SearchView.SearchAutoComplete searchAutoComplete = searchView.findViewById(R.id.search_src_text);
        // 提示字符为白色
        searchAutoComplete.setHintTextColor(Color.WHITE);
        // 输入文本字符为白色
        searchAutoComplete.setTextColor(Color.WHITE);
        // 默认没有焦点
        searchAutoComplete.clearFocus();
        searchView.clearFocus();
    }

    private void initFAB() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 添加联系人
            }
        });
    }

    private void initContactsLoader() {
        LoaderManager.getInstance(this).initLoader(LOADER_ID_CONTACTS, null, this);
    }

    private void requestReadContactsPermission() {
        ActivityCompat.requestPermissions(this, new String[]{READ_CONTACTS_PERMISSION},
                REQUEST_CODE_READ_CONTACTS);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_READ_CONTACTS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initContactsLoader();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        if (id == LOADER_ID_CONTACTS) {
            return new CursorLoader(this, ContactsQuery.URI, ContactsQuery.PROJECTION,
                    null, null, ContactsQuery.SORT_KEY);
        }
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if (loader.getId() == LOADER_ID_CONTACTS) {
            mAdapter.swapCursor(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        if (loader.getId() == LOADER_ID_CONTACTS) {
            mAdapter.swapCursor(null);
        }
    }

    @Override
    public void onPass() {
        requestReadContactsPermission();
    }
}
