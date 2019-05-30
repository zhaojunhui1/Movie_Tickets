package com.bw.movie.zjh.utils.mvp.presenter;
import com.bw.movie.zjh.utils.mvp.callback.MyCallBack;
import com.bw.movie.zjh.utils.mvp.model.IModelImpl;
import com.bw.movie.zjh.utils.mvp.view.IView;

import java.util.Map;
import okhttp3.RequestBody;

/**
 * author : zjh
 * e-mail : zjh@163.com
 * date   : 2019/5/10 09:10
 * desc   :
 * version: 1.0
 */
public class IPresenterImpl implements IPresenter {
    private IView iView;
    private IModelImpl iModel;

    public IPresenterImpl(IView iView) {
        this.iView = iView;
        iModel = new IModelImpl();
    }

    /*
     *  get 请求方式
     * */
    @Override
    public void getPresenterData(String url, Map<String, String> map, Class clazz) {
        iModel.getModelData(url, map, clazz, new MyCallBack() {
            @Override
            public void OnSuccess(Object data) {
                iView.viewDataSuccess(data);
            }

            @Override
            public void OnFails(String error) {
                //iView.viewDataFails(error);
            }
        });
    }

    /*
     *  post 请求方式
     * */
    @Override
    public void postPresenterData(String url, Map<String, String> map, Class clazz) {
        iModel.postModelData(url, map, clazz, new MyCallBack() {
            @Override
            public void OnSuccess(Object data) {
                iView.viewDataSuccess(data);
            }

            @Override
            public void OnFails(String error) {
                //iView.viewDataFails(error);
            }
        });
    }

    /*
     *  put 请求方式
     * */
    @Override
    public void putPresenterData(String url, Map<String, String> map, Class clazz) {
        iModel.putModelData(url, map, clazz, new MyCallBack() {
            @Override
            public void OnSuccess(Object data) {
                iView.viewDataSuccess(data);
            }

            @Override
            public void OnFails(String error) {
                //iView.viewDataFails(error);
            }
        });
    }

    /*
     *  delete 请求方式
     * */
    @Override
    public void deletePresenterData(String url, Map<String, String> map, Class clazz) {
        iModel.deleteModelData(url, map, clazz, new MyCallBack() {
            @Override
            public void OnSuccess(Object data) {
                iView.viewDataSuccess(data);
            }

            @Override
            public void OnFails(String error) {
                //iView.viewDataFails(error);
            }
        });
    }

    /*
     *   body请求体
     * */
    @Override
    public void bodyPresenterData(String url, Map<String, String> map, Map<String, RequestBody> mapBody, Class clazz) {
        iModel.mBodyRequest(url, map, mapBody, clazz, new MyCallBack() {
            @Override
            public void OnSuccess(Object data) {
                iView.viewDataSuccess(data);
            }

            @Override
            public void OnFails(String error) {
                //iView.viewDataFails(error);
            }
        });
    }

    /*
     *   post 表单请求
     * */
    @Override
    public void postLoginPresenterData(String url, Map<String, String> map, Class clazz) {
        iModel.postLoginModelRequest(url, map, clazz, new MyCallBack() {
            @Override
            public void OnSuccess(Object data) {
                iView.viewDataSuccess(data);
            }

            @Override
            public void OnFails(String error) {
                //iView.viewDataFails(error);
            }
        });
    }

    /*
     *   关闭view层的强引用
     *   避免mvp内存泄漏
     * */
    public void onDetach() {
        if (iView != null) {
            iView = null;
        }
        if (iModel != null) {
            iModel = null;
        }
    }
}
