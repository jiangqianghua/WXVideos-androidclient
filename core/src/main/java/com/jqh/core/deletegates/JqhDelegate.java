package com.jqh.core.deletegates;

public abstract class JqhDelegate extends PermissionCheckerDelegate {
    // 获得父类的fragment
    public <T extends JqhDelegate> T getParentDelegate(){
        return (T) getParentFragment();
    }

}
