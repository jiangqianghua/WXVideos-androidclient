package com.jqh.core.recycler;

/**
 * 分页
 */
public class PageBean {

    private int mPageIndex = 0 ;

    private int mTotal = 0 ;

    private int mPageSize = 0 ;

    private int mCurrentCount = 0 ;

    private int mDelayed = 0 ;

    public int getmPageIndex() {
        return mPageIndex;
    }

    public PageBean setmPageIndex(int mPageIndex) {
        this.mPageIndex = mPageIndex;
        return this ;
    }

    public int getmTotal() {
        return mTotal;
    }

    public PageBean setmTotal(int mTotal) {
        this.mTotal = mTotal;
        return this ;
    }

    public int getmPageSize() {
        return mPageSize;
    }

    public PageBean setmPageSize(int mPageSize) {
        this.mPageSize = mPageSize;
        return this ;
    }

    public int getmCurrentCount() {
        return mCurrentCount;
    }

    public PageBean setmCurrentCount(int mCurrentCount) {
        this.mCurrentCount = mCurrentCount;
        return this ;
    }

    public int getmDelayed() {
        return mDelayed;
    }

    public PageBean setmDelayed(int mDelayed) {
        this.mDelayed = mDelayed;
        return this ;
    }

    public PageBean addIndex(){
        mPageIndex++;
        return this ;
    }
}
