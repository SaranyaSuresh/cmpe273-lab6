package edu.sjsu.cmpe.cache.client;

import java.util.SortedMap;
import java.util.TreeMap;
import java.util.List;

import com.google.common.base.Charsets;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

public class ServerHash<T>
{
	private final HashFunction hashFunction;
	private final SortedMap<Integer, T> circle = new TreeMap<Integer, T>();
	
	  public ServerHash(HashFunction hashFunction, List<T> nodes) 
	  {
	    this.hashFunction = Hashing.md5();
	    for (T node : nodes) 
	    {
	      add(node);
	    }
	  }

	  public void add(T node) 
	  {
		  circle.put(hashFunction.hashString(node.toString()).asInt(), node);
	    
	  }

	  public T get(int key) 
	  {
	    if (circle.isEmpty()) 
	    {
	      return null;
	    }
	    int hash = hashFunction.hashInt(key).asInt();
	    if (!circle.containsKey(hash)) 
	    {
	      SortedMap<Integer, T> tailMap =
	        circle.tailMap(hash);
	      hash = tailMap.isEmpty() ?
	             circle.firstKey() : tailMap.firstKey();
	    }
	    return circle.get(hash);
	  } 

	}



