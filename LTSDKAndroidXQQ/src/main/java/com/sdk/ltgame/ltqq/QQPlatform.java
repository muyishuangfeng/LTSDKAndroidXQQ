package com.sdk.ltgame.ltqq;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.sdk.ltgame.core.common.LTGameOptions;
import com.sdk.ltgame.core.common.LTGameSdk;
import com.sdk.ltgame.core.common.Target;
import com.sdk.ltgame.core.impl.OnLoginStateListener;
import com.sdk.ltgame.core.impl.OnRechargeListener;
import com.sdk.ltgame.core.model.LoginObject;
import com.sdk.ltgame.core.model.RechargeObject;
import com.sdk.ltgame.core.platform.AbsPlatform;
import com.sdk.ltgame.core.platform.IPlatform;
import com.sdk.ltgame.core.platform.PlatformFactory;
import com.sdk.ltgame.core.uikit.BaseActionActivity;
import com.sdk.ltgame.core.util.LTGameUtil;
import com.sdk.ltgame.ltqq.uikit.QQActionActivity;


public class QQPlatform extends AbsPlatform {

    private QQHelper mHelper;

    private QQPlatform(Context context, boolean isServerTest, String appId, String appName, String appKey,
                       int target) {
        super(context, isServerTest, appId, appName, appKey, target);
    }

    @Override
    public void recharge(Activity activity, int target, RechargeObject object, OnRechargeListener listener) {

    }

    @Override
    public void login(Activity activity, int target, LoginObject object, OnLoginStateListener listener) {
        mHelper = new QQHelper(activity, object.getQqAppID(), object.getmAdID(), object.isLoginOut(), target, listener);
        mHelper.loginAction();
    }

    @Override
    public Class getUIKitClazz() {
        return QQActionActivity.class;
    }

    @Override
    public void onActivityResult(BaseActionActivity activity, int requestCode, int resultCode, Intent data) {
        mHelper.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 工厂类
     */
    public static class Factory implements PlatformFactory {

        @Override
        public IPlatform create(Context context, int target) {
            IPlatform platform = null;
            LTGameOptions options = LTGameSdk.options();
            if (!LTGameUtil.isAnyEmpty(options.getLtAppId(), options.getLtAppKey(),
                    options.getQqAppId())) {
                platform = new QQPlatform(context, options.getISServerTest(), options.getLtAppId(), options.getLtAppKey(),
                        options.getQqAppId(), target);
            }
            return platform;
        }

        @Override
        public int getPlatformTarget() {
            return Target.PLATFORM_QQ;
        }

        @Override
        public boolean checkLoginPlatformTarget(int target) {
            return target == Target.LOGIN_QQ;
        }

        @Override
        public boolean checkRechargePlatformTarget(int target) {
            return false;
        }
    }
}
