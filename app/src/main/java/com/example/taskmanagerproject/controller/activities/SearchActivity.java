package com.example.taskmanagerproject.controller.activities;

import android.app.SearchManager;
import android.content.Context;
import android.view.Menu;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;

import com.example.taskmanagerproject.R;
import com.example.taskmanagerproject.controller.fragments.TaskListFragment;
import com.example.taskmanagerproject.repository.TaskDBRepository;

public class SearchActivity extends SingleFragmentActivity {

    TaskDBRepository mRepository = TaskDBRepository.getInstance(this, 0);

    @Override
    public Fragment createFragment() {
        return TaskListFragment.newInstance(mRepository.getTasks(), 3);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_task_search, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

//        Intent intent = new Intent(this, SearchResultActivity.class);
//        startActivity(intent);
        return true;
    }


//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.app_bar_search:
//                //todo
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
}