package com.bw.movie.zjh.ui.cinema.my;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.zjh.base.BaseActivity;
import com.bw.movie.zjh.beans.my.MyBackMessageBean;
import com.bw.movie.zjh.utils.mvp.presenter.IPresenterImpl;
import com.bw.movie.zjh.utils.mvp.util.Apis;
import com.bw.movie.zjh.utils.mvp.view.IView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * author : zjh
 * e-mail : zjh@163.com
 * date   : 2019/5/13 19:42
 * desc   :  意见反馈
 * version: 1.0
 */
public class MyBackMessageActivity extends BaseActivity implements IView {

    private Unbinder bind;
    private IPresenterImpl iPresenter;
    @BindView(R.id.back_message_edit)
    EditText back_message_edit;

    @BindView(R.id.backMessage)
    LinearLayout backMessage;
    @BindView(R.id.success)
    LinearLayout success;

    @Override
    public int bindLayout() {
        return R.layout.activity_my_back_message;
    }

    @Override
    protected void initView() {
        bind = ButterKnife.bind(this);
        iPresenter = new IPresenterImpl(this);
    }

    @Override
    protected void initData() {

    }

    /*
     *   点击事件绑定
     *  */
    @OnClick({R.id.back_submit, R.id.back_message_return})
    public void messageClick(View v) {
        switch (v.getId()) {
            case R.id.back_submit:   //点击提交
                if (back_message_edit.getText().toString().length() == 0) {
                    Toast.makeText(this, "多少写点什么吧!", Toast.LENGTH_SHORT).show();
                }else {
                    Map<String, String> map = new HashMap<>();
                    map.put("content", back_message_edit.getText().toString());
                    iPresenter.postLoginPresenterData(Apis.MY_BACE_MESSAGE_POST, map, MyBackMessageBean.class);
                }
                break;
            case R.id.back_message_return:
                this.finish();
                break;
            default:
                break;
        }
    }

    /*
     *   回调函数
     *  */
    @Override
    public void viewDataSuccess(Object data) {
        if (data instanceof MyBackMessageBean) {
            MyBackMessageBean backMessageBean = (MyBackMessageBean) data;
            Toast.makeText(this, backMessageBean.getMessage(), Toast.LENGTH_SHORT).show();
            if (backMessageBean.getStatus().equals("0000")) {
                //反馈成功
                backMessage.setVisibility(View.GONE);
                success.setVisibility(View.VISIBLE);
            } else {
                backMessage.setVisibility(View.VISIBLE);
                success.setVisibility(View.GONE);
            }
        }

    }

    /*
     *  内存处理
     * */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
        iPresenter.onDetach();
    }

}
