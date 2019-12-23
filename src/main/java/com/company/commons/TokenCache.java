package com.company.commons;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class TokenCache {
    private static final Logger logger =LoggerFactory.getLogger(TokenCache.class);
    public static final String TOKEN_PREFIX="token_";
    //创建一个guava的本地缓存LocalCache对象
    private static LoadingCache<String,String> loadingCache = CacheBuilder.newBuilder()
            .initialCapacity(1000)
            .maximumSize(10000)    //超过最大值，则使用LRU算法删除缓存数据
            .expireAfterAccess(12, TimeUnit.HOURS)
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String s) throws Exception {
                    return null;   //当key没有中时，guava会自动调用load方法，返回指定数据，默认为null值
                }
            });
    public static void setKey(String key,String value){
        loadingCache.put(key, value);
    }
    public static String getKey(String key){
        String value =null;
        try {
            value =loadingCache.get(key);
        } catch (Exception e) {
            logger.error("LocalCache",e);
        }
        return value;
    }
}
