package com.misha.application.utils;

/**
 * Created by misha on 18.11.16.
 */
public class PaginationUtils {
    public static final int PAGE_SIZE = 15;

    public static int getStartRecord(int page) {
        return page * PAGE_SIZE;
    }
}
