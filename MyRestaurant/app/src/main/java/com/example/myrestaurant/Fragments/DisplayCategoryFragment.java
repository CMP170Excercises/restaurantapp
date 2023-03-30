package com.example.myrestaurant.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myrestaurant.Activity.AddCategoryActivity;
import com.example.myrestaurant.Activity.HomeActivity;
import com.example.myrestaurant.Adapter.AdapterDisplayCategory;
import com.example.myrestaurant.DAO.DishCategoryDAO;
import com.example.myrestaurant.Model.DishCategory;
import com.example.myrestaurant.R;

import java.util.List;

public class DisplayCategoryFragment extends Fragment {
    GridView gvCategory;
    List<DishCategory> dishCategoryList;
    DishCategoryDAO dishCategoryDAO;
    AdapterDisplayCategory adapterDisplayCategory;
    FragmentManager fragmentManager;
    int tableId;

    ActivityResultLauncher<Intent> resultLauncherCategory = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == Activity.RESULT_OK){
                Intent intent = result.getData();
                boolean ktra = intent.getBooleanExtra("ktra",false);
                String function = intent.getStringExtra("function");
                if(function.equals("addcategory")) {
                    if(ktra) {
                        ShowListCategory();
                        Toast.makeText(getActivity(),"Add Successfully!",Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(),"Add Failed!",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if(ktra) {
                        ShowListCategory();
                        Toast.makeText(getActivity(),"Edit Successfully!",Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(),"Edit Failed!",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_display_category, container, false);
        setHasOptionsMenu(true);
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Menu Management");

        gvCategory = (GridView)view.findViewById(R.id.gvCategory);

        fragmentManager = getActivity().getSupportFragmentManager();

        dishCategoryDAO = new DishCategoryDAO(getActivity());
        ShowListCategory();

        Bundle bDataCategory = getArguments();
        if(bDataCategory != null) {
            tableId = bDataCategory.getInt("tableId");
        }

        gvCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int categoryId = dishCategoryList.get(position).getCategoryId();
                String categoryName = dishCategoryList.get(position).getCategoryName();
                DisplayMenuFragment displayMenuFragment = new DisplayMenuFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("categoryId", categoryId);
                bundle.putString("categoryName", categoryName);
                bundle.putInt("tableId", tableId);
                displayMenuFragment.setArguments(bundle);

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.contentView, displayMenuFragment).addToBackStack("displaycategory");
                transaction.commit();
            }
        });

        registerForContextMenu(gvCategory);

        return view;
    }

    //hiển thị contextmenu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.edit_context_menu,menu);
    }

    //xử lí context menu
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int pos = menuInfo.position;
        int categoryId = dishCategoryList.get(pos).getCategoryId();

        switch (id){
            case R.id.itEdit:
                Intent iEdit = new Intent(getActivity(), AddCategoryActivity.class);
                iEdit.putExtra("categoryId", categoryId);
                resultLauncherCategory.launch(iEdit);
                break;

            case R.id.itDelete:
                boolean ktra = dishCategoryDAO.DeleteCategory(categoryId);
                if(ktra) {
                    ShowListCategory();
                    Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.delete_sucessful), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.delete_failed), Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return true;
    }

    //khởi tạo nút thêm loại
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem itAddCategory = menu.add(1, R.id.itAddCategory, 1, R.string.addCategory);
        itAddCategory.setIcon(R.drawable.ic_baseline_add_24);
        itAddCategory.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }

    //xử lý nút thêm loại
    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.itAddCategory:
                Intent intent = new Intent(getActivity(), AddCategoryActivity.class);
                resultLauncherCategory.launch(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //hiển thị dữ liệu trên gridview
    private void ShowListCategory(){
        dishCategoryList = dishCategoryDAO.SelectCategoryList();
        adapterDisplayCategory = new AdapterDisplayCategory(getActivity(), R.layout.custom_layout_display_category, dishCategoryList);
        gvCategory.setAdapter(adapterDisplayCategory);
        adapterDisplayCategory.notifyDataSetChanged();
    }
}
