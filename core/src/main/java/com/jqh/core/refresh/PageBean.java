package com.jqh.core.refresh;

/**
 * 分页组件
 */
public class PageBean {

    // 当前是第几页
    private int mPageIndex = 0 ;
    // 总的条目数
    private int mTotal = 0 ;
    // 总的页数
    private int mPageSize = 0 ;
    // 当前数据size
    private int mCurrentCount = 0 ;

    private int mDelayed = 0 ;

    public int getPageIndex() {
        return mPageIndex;
    }

    public PageBean setPageIndex(int mPageIndex) {
        this.mPageIndex = mPageIndex;
        return this ;
    }

    public int getTotal() {
        return mTotal;
    }

    public PageBean setTotal(int mTotal) {
        this.mTotal = mTotal;
        return this ;
    }

    public int getPageSize() {
        return mPageSize;
    }

    public PageBean setPageSize(int mPageSize) {
        this.mPageSize = mPageSize;
        return this ;
    }

    public int getCurrentCount() {
        return mCurrentCount;
    }

    public PageBean setCurrentCount(int mCurrentCount) {
        this.mCurrentCount = mCurrentCount;
        return this ;
    }

    public int getDelayed() {
        return mDelayed;
    }

    public PageBean setDelayed(int mDelayed) {
        this.mDelayed = mDelayed;
        return this ;
    }

    public PageBean addIndex(){
        mPageIndex++;
        return this ;
    }
}
