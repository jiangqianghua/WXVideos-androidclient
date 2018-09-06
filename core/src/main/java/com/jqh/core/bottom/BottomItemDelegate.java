package com.jqh.core.bottom;

import android.widget.Toast;

import com.jqh.core.R;
import com.jqh.core.app.Jqh;
import com.jqh.core.deletegates.JqhDelegate;

/**
 * Created by jiangqianghua on 18/7/28.
 */

public abstract class BottomItemDelegate extends JqhDelegate {
    // 再点一次退出程序时间设置
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;

    @Override
    public boolean onBackPressedSupport() {
        if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
            _mActivity.finish();
        } else {
            TOUCH_TIME = System.currentTimeMillis();
            Toast.makeText(_mActivity, "双击退出" + Jqh.getApplicationContext().getString(R.string.app_name), Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}
