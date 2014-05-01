package edu.sjsu.cmpe.cache.client;

import java.util.List;
import java.util.ArrayList;
import com.google.common.hash.Hashing;


public class Client {
	
	public static void main(String[] args) throws Exception {
        System.out.println("Starting Cache Client...");
        CacheServiceInterface cache_1 = new DistributedCacheService("http://localhost:3000");
        CacheServiceInterface cache_2 = new DistributedCacheService("http://localhost:3001");
        CacheServiceInterface cache_3 = new DistributedCacheService("http://localhost:3002");

        List<CacheServiceInterface> servers_array = new ArrayList<CacheServiceInterface>();
        servers_array.add(cache_1);
        servers_array.add(cache_2);
        servers_array.add(cache_3);
        
        ServerHash<CacheServiceInterface> serverhash = new ServerHash(Hashing.md5(), servers_array);
                
        char value ='a';
        for(int key=1; key<=10; key++)
        {
        	int bucket = Hashing.consistentHash(Hashing.md5().hashString(Integer.toString(key)), servers_array.size());
        	CacheServiceInterface shard = serverhash.get(bucket);
        	shard.put(key, Character.toString(value));
        	System.out.println("put("+key +"=>"+ value+")");
        	System.out.println("get("+key +") =>"+ shard.get(key) +  " from " + shard.getCacheServerUrl());
            value++;
        }
        
        System.out.println("Exiting Cache Client...");
        
        
    }

}


