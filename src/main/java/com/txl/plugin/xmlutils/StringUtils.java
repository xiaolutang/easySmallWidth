package com.txl.plugin.xmlutils;

/**
 * Copyright (c) 2021 唐小陆 All rights reserved.
 * author：txl
 * date：2021/1/20
 * description：
 */
public class StringUtils {
    public static boolean isEmpty(CharSequence s) {
        if (s == null) {
            return true;
        } else {
            return s.length() == 0;
        }
    }
}
