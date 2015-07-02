package com.nickycho.popupmenuexample;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i = 0; i < 19; i++) {
            int j = i / 2;
            int k = i % 2;
            Log.e("","i:"+i+" j:"+j+" k:"+k);
        }

        Button popupMenuBtn = (Button)findViewById(R.id.popupMenuBtn);
        final Button popupWidowBtn = (Button)findViewById(R.id.popupWidowBtn);



        View popupWindowLayout = getLayoutInflater().inflate(R.layout.popup_window_layout, null);
        popupWindow = new PopupWindow(popupWindowLayout, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
//        popupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.abc_cab_background_internal_bg));
        LinearLayout popupWindowCopyLl = (LinearLayout) popupWindowLayout.findViewById(R.id.popupWindow_copyLl);

        popupWindowCopyLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeToast("popupwindow copy");
                popupWindow.dismiss();
            }
        });

        popupWidowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popupWindow.showAsDropDown(v);

            }
        });





        popupMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, view);
                try {
                    Field[] fields = popupMenu.getClass().getDeclaredFields();
                    for (Field field : fields) {
                        if ("mPopup".equals(field.getName())) {
                            field.setAccessible(true);
                            Object menuPopupHelper = field.get(popupMenu);
                            Class<?> classPopupHelper = Class.forName(menuPopupHelper
                                    .getClass().getName());
                            Method setForceIcons = classPopupHelper.getMethod(
                                    "setForceShowIcon", boolean.class);
                            setForceIcons.invoke(menuPopupHelper, true);
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.menu_copy:
                                makeToast("copy");
                                break;
                            case R.id.menu_paste:
                                makeToast("paste");
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.inflate(R.menu.menu_popupmenu);
                popupMenu.show();
            }
        });
    }

    public void makeToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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
}
