package com.jqh.core.deletegates.web.event;

import com.jqh.core.util.log.JqhLogger;

/**
 * Created by 傅令杰
 */

public class UndefineEvent extends Event {
    @Override
    public String execute(String params) {
        JqhLogger.e("UndefineEvent", params);
        return null;
    }
}
