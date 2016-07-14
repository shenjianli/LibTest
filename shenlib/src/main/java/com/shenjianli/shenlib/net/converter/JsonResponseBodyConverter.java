package com.shenjianli.shenlib.net.converter;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

public class JsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

	private final Gson mGson;// gson对象
	private final TypeAdapter<T> adapter;

	/**
	 * 构造器
	 */
	public JsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
		this.mGson = gson;
		this.adapter = adapter;
	}

	/**
	 * 转换
	 * 
	 * @param responseBody
	 * @return
	 * @throws IOException
	 */
	@Override
	public T convert(ResponseBody value) throws IOException {

		JsonReader jsonReader = mGson.newJsonReader(value.charStream());
		try {
			return adapter.read(jsonReader);
		} finally {
			value.close();
		}
	}

}
