package com.bw.movie.fmk.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.fmk.activity.LogActivity;
import com.bw.movie.fmk.bean.TouXiangBean;
import com.bw.movie.fmk.mvp.p.MyPensterTwo;
import com.bw.movie.fmk.mvp.p.PInterface;
import com.bw.movie.fmk.mvp.v.VInterface;

import com.bw.movie.zjh.base.BaseFragment;
import com.bw.movie.zjh.beans.my.MyUserSignInBean;
import com.bw.movie.zjh.ui.cinema.my.MyBackMessageActivity;
import com.bw.movie.zjh.ui.cinema.my.MyFoodedActivity;
import com.bw.movie.zjh.ui.cinema.my.MyLookActivity;
import com.bw.movie.zjh.ui.cinema.my.MyPresonActivity;
import com.bw.movie.zjh.ui.cinema.my.MyRemindActivity;
import com.bw.movie.zjh.utils.mvp.presenter.IPresenterImpl;
import com.bw.movie.zjh.utils.mvp.util.Apis;
import com.bw.movie.zjh.utils.mvp.view.IView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @Auther: 付明锟
 * @Date: 2019/5/12 18:42
 * @Description:
 */
public class FragmentThree extends BaseFragment implements IView, View.OnClickListener ,VInterface.VInterfacegetTouXiang {

    private IPresenterImpl iPresenter;
    private PInterface.PInterfacegetTouXiang pInterfacegetTouXiang;
    private List<TouXiangBean> touXiangBeans = new ArrayList<>();
    private Dialog dialog;
    private Button pai;
    private Button xiang;
    private Button xiao;
    private SimpleDraweeView my_save_image;

    @Override
    protected int setView() {
        return R.layout.fragment_three;
    }

    @Override
    protected void init(View view) {
        iPresenter = new IPresenterImpl(this);
        view.findViewById(R.id.my_remind).setOnClickListener(this);
        view.findViewById(R.id.my_save_image).setOnClickListener(this);
        my_save_image = view.findViewById(R.id.my_save_image);
        view.findViewById(R.id.my_sign_in).setOnClickListener(this);
        view.findViewById(R.id.my_preson).setOnClickListener(this);
        view.findViewById(R.id.my_look).setOnClickListener(this);
        view.findViewById(R.id.my_fooded).setOnClickListener(this);
        view.findViewById(R.id.my_back_message).setOnClickListener(this);
        view.findViewById(R.id.my_newapp).setOnClickListener(this);
        view.findViewById(R.id.my_unlogin).setOnClickListener(this);
        //头像
        pInterfacegetTouXiang = new MyPensterTwo(this);


    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    /*
     *   点击事件
     * */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_remind:      //系统消息
                startActivity(new Intent(getActivity(), MyRemindActivity.class));
                break;
            case R.id.my_save_image:  //上传头像

                dialog = new Dialog(getActivity(), R.style.ActionSheetDialogStyle);

                View view = LayoutInflater.from(getActivity()).inflate(R.layout.wode_pop, null, false);

                //初始化控件
                pai = view.findViewById(R.id.pai);
                xiang = view.findViewById(R.id.xiang);
                xiao = view.findViewById(R.id.xiao);
                //将布局设置给Dialog
                dialog.setContentView(view);
                //获取当前Activity所在的窗体
                Window window = dialog.getWindow();
                if (window == null) {
                    return;
                }
                //从底部弹出
                window.setGravity(Gravity.BOTTOM);
                //获得窗体的属性
                WindowManager.LayoutParams attributes = window.getAttributes();
                //距离底部的距离
                attributes.y = 40;
                //将属性设置给窗体
                window.setAttributes(attributes);

                //相机
                //xiangji();

                //相册
                xiangce();

                //取消
                quxiao();

                //对话框
                dialog.show();

                break;
            case R.id.my_sign_in:     // 签到
                initUserSignIn();
                break;
            case R.id.my_preson:      // 我的资料
                startActivity(new Intent(getActivity(), MyPresonActivity.class));
                break;
            case R.id.my_look:        //我的关注
                startActivity(new Intent(getActivity(), MyLookActivity.class));
                break;
            case R.id.my_fooded:      //购票记录
                startActivity(new Intent(getActivity(), MyFoodedActivity.class));
                break;
            case R.id.my_back_message:// 意见反馈
                startActivity(new Intent(getActivity(), MyBackMessageActivity.class));
                break;
            case R.id.my_newapp:       // 最新版本
                findNewVersion();
                break;
            case R.id.my_unlogin:      //退出登录
                Intent intent2 = new Intent(getActivity(), LogActivity.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent2);
                break;
            default:
                break;
        }
    }


    //相机
//    private void xiangji() {
//        pai.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                //打开相机
////                Intent intent_takePhoto=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
////                if (hasSdcard()){//判断SD卡是否可用
////                    file=new File(Environment.getExternalStorageDirectory(),PHOTO_FILE_MAME);
////                    //存放到内存中
////                    intent_takePhoto.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(path)));
////                }
////                startActivityForResult(intent_takePhoto, 1);
////                dialog.dismiss();
//            }
//        });
//    }

    //取消
    private void quxiao() {
        xiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


    //跳转到相册
    private void xiangce() {
        xiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent,2);
                dialog.dismiss();
            }
        });
    }

    //相册
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        File file = null;
        //从相册中取出照片
        Uri uri = data.getData();
        my_save_image.setImageURI(uri);
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),uri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //把图片转成File格式，获取SD卡路径
        String  path = Environment.getExternalStorageDirectory() + "/fmktou";
        //保存图片的路径
        File file1 = new File(path);
        //判断文件夹是否存在,如果存在打印log;不存在，创建文件夹
        if (!file1.exists()){
            file1.mkdir();
        }
        file = new File(file,"1234.png");

        try {
            //将图片用流写进去
            BufferedOutputStream buff = new BufferedOutputStream(new FileOutputStream(file));
            //进行压缩
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,buff);

            //刷新缓冲区
            buff.flush();
            //关闭流
            buff.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Shc(file);
    }

    //相册
    private void Shc(File file22) {
        //application/otcet-stream固定值
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/otcet-stream"), file22);
        //文件的key就是image
        MultipartBody.Part image = MultipartBody.Part.createFormData("image", file22.getName(), requestBody);
       // pInterfacegetTouXiang.getTouXiang(null, image);
        Log.e("tab","pInterfacegetTouXiang=="+image);
    }

    //头像回传
    @Override
    public void getTouXiang(Object object) {
        TouXiangBean touXiangBean2 = (TouXiangBean)object;
        String headPath = touXiangBean2.getHeadPath();
        //my_save_image.setImageURI(Uri.parse(headPath));
        String message = touXiangBean2.getMessage();
        Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();
        touXiangBeans.add(touXiangBean2);
    }

    //判断SD卡是否挂载
    public boolean hasSdcard() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }


  /*  *  检查版本
    * */
    private void findNewVersion() {
        Toast.makeText(getActivity(), "已经是最新版本了", Toast.LENGTH_SHORT).show();

    }

    /*
     *   用户签到
     *  */
    private void initUserSignIn() {
        Map<String, String> map1 = new HashMap<>();
        iPresenter.getPresenterData(Apis.MY_USER_SIGNIN_GET, map1, MyUserSignInBean.class);
    }

    /*
     *   回调函数
     * */
    @Override
    public void viewDataSuccess(Object data) {
        if (data instanceof MyUserSignInBean) {
            MyUserSignInBean signInBean = (MyUserSignInBean) data;
            Toast.makeText(getActivity(), signInBean.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /*
    *  内存处理
    * */
    @Override
    public void onDestroy() {
        super.onDestroy();
        iPresenter.onDetach();
    }


}
