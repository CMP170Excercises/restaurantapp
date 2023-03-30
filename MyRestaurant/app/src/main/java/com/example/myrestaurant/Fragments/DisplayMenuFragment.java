package com.example.myrestaurant.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.KeyEvent;
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

import com.example.myrestaurant.Activity.AddMenuActivity;
import com.example.myrestaurant.Activity.AmountMenuActivity;
import com.example.myrestaurant.Activity.HomeActivity;
import com.example.myrestaurant.Adapter.AdapterDisplayMenu;
import com.example.myrestaurant.DAO.DishDAO;
import com.example.myrestaurant.Model.Dish;
import com.example.myrestaurant.R;

import java.util.List;

public class DisplayMenuFragment extends Fragment {
    int categoryId, tableId;
    String categoryName, state;
    GridView gvDisplayMenu;
    DishDAO dishDAO;
    List<Dish> dishList;
    AdapterDisplayMenu adapterDisplayMenu;

    ActivityResultLauncher<Intent> resultLauncherMenu = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == Activity.RESULT_OK) {
                Intent intent = result.getData();
                boolean ktra = intent.getBooleanExtra("ktra",false);
                String function = intent.getStringExtra("function");
                if(function.equals("adddish")) {
                    if(ktra) {
                        ShowListDish();
                        Toast.makeText(getActivity(),"Add Successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(),"Add Failed!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if(ktra) {
                        ShowListDish();
                        Toast.makeText(getActivity(),"Add Successfully!",Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(),"Add Failed!",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    });


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_display_menu, container, false);
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Menu Management");
        dishDAO = new DishDAO(getActivity());

        gvDisplayMenu = (GridView)view.findViewById(R.id.gvDisplayMenu);

        Bundle bundle = getArguments();
        if(bundle != null){
            categoryId = bundle.getInt("categoryId");
            categoryName = bundle.getString("categoryName");
            tableId = bundle.getInt("tableId");
            ShowListDish();

            gvDisplayMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //nếu lấy đc mã bàn mới mở
                    state = dishList.get(position).getState();
                    if(tableId != 0){
                        if(state.equals("true")) {
                            Intent iAmount = new Intent(getActivity(), AmountMenuActivity.class);
                            iAmount.putExtra("tableId", tableId);
                            iAmount.putExtra("dishId", dishList.get(position).getDishId());
                            startActivity(iAmount);
                        } else {
                            Toast.makeText(getActivity(),"Out of dish, can't add", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
        setHasOptionsMenu(true);
        registerForContextMenu(gvDisplayMenu);
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN){
                    getParentFragmentManager().popBackStack("displaycategory", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
                return false;
            }
        });

        return view;
    }

    //tạo 1 menu context show lựa chọn
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.edit_context_menu,menu);
    }

    //Tạo phần sửa và xóa trong menu context
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int pos = menuInfo.position;
        int dishId = dishList.get(pos).getDishId();

        switch (id){
            case R.id.itEdit:
                Intent iEdit = new Intent(getActivity(), AddMenuActivity.class);
                iEdit.putExtra("dishId", dishId);
                iEdit.putExtra("categoryId", categoryId);
                iEdit.putExtra("categoryName", categoryName);
                resultLauncherMenu.launch(iEdit);
                break;

            case R.id.itDelete:
                boolean ktra = dishDAO.DeleteDish(dishId);
                if(ktra) {
                    ShowListDish();
                    Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.delete_sucessful)
                            ,Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.delete_failed)
                            ,Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem itAddMenu = menu.add(1, R.id.itAddMenu, 1, R.string.addMenu);
        itAddMenu.setIcon(R.drawable.ic_baseline_add_24);
        itAddMenu.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.itAddMenu:
                Intent intent = new Intent(getActivity(), AddMenuActivity.class);
                intent.putExtra("categoryId", categoryId);
                intent.putExtra("categoryName", categoryName);
                resultLauncherMenu.launch(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void ShowListDish(){
        dishList = dishDAO.SelectDishListByCategory(categoryId);
        adapterDisplayMenu = new AdapterDisplayMenu(getActivity(),R.layout.custom_layout_display_menu, dishList);
        gvDisplayMenu.setAdapter(adapterDisplayMenu);
        adapterDisplayMenu.notifyDataSetChanged();
    }
}
