package com.txl.plugin.xmlutils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;

public class FileX extends File
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5613203194582469352L;

	public FileX(String pathname)
	{
		super(pathname);
	}

	public FileX(URI uri)
	{
		super(uri);
	}

	public FileX(String parent, String child)
	{
		super(parent, child);
	}

	public FileX(File parent, String child)
	{
		super(parent, child);
	}

	@Override
	public String getPath()
	{
		try
		{
			return URLDecoder.decode(super.getPath(),"UTF-8");
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		return super.getPath();
	}

	@Override
	public String getAbsolutePath()
	{
		try
		{
			return URLDecoder.decode(super.getAbsolutePath(),"UTF-8");
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		return super.getAbsolutePath();
	}



}
