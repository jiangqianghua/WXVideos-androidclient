package com.jqh.core.util.timer;

import java.util.TimerTask;

/**
 * Created by jiangqianghua on 18/3/7.
 */

public class BaseTimerTask extends TimerTask {

    private ITimerListener mITimerListener ;

    public BaseTimerTask(ITimerListener timerListener) {
        this.mITimerListener = timerListener;
    }

    @Override
    public void run() {
        if(mITimerListener != null){
            mITimerListener.onTimer();
        }
    }
}
