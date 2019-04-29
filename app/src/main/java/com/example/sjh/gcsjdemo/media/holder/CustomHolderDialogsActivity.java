package com.example.sjh.gcsjdemo.media.holder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.sjh.gcsjdemo.R;
import com.example.sjh.gcsjdemo.media.DemoDialogsActivity;
import com.example.sjh.gcsjdemo.media.data.fixtures.DialogsFixtures;
import com.example.sjh.gcsjdemo.media.holder.holders.dialogs.CustomDialogViewHolder;
import com.stfalcon.chatkit.dialogs.DialogsList;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;

import com.example.sjh.gcsjdemo.media.data.model.Dialog;


public class CustomHolderDialogsActivity extends DemoDialogsActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, CustomHolderDialogsActivity.class));
    }

    private DialogsList dialogsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_holder_dialogs);
        //会话列表
        dialogsList = (DialogsList) findViewById(R.id.dialogsList);
        //初始化适配器并交给List
        initAdapter();
    }

    //当点击会话跳转activity到下一层，并在这里吧dialog传入
    @Override
    public void onDialogClick(Dialog dialog) {
        CustomHolderMessagesActivity.open(this);
    }

    private void initAdapter() {
        //初始化会话列表适配器
        super.dialogsAdapter = new DialogsListAdapter<>(
                R.layout.item_custom_dialog_view_holder,
                CustomDialogViewHolder.class,
                super.imageLoader);
        //设置适配器中item内容
        super.dialogsAdapter.setItems(DialogsFixtures.getDialogs());
        //设置监听器，长按或者点击的时间
        super.dialogsAdapter.setOnDialogClickListener(this);
        super.dialogsAdapter.setOnDialogLongClickListener(this);
        //将设置好的适配器配置给List
        dialogsList.setAdapter(super.dialogsAdapter);
    }
}
