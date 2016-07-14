package com.shenjianli.shenlib.net.converter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.ByteString;
import retrofit2.Converter;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonWriter;

public class JsonRequestBodyConverter<T> implements Converter<T, RequestBody> {

	private static final MediaType MEDIA_TYPE = MediaType
			.parse("application/json; charset=UTF-8");
	private static final Charset UTF_8 = Charset.forName("UTF-8");

	private final Gson gson;
	private final TypeAdapter<T> adapter;

	/**
	 * 构造器
	 */

	public JsonRequestBodyConverter(Gson gson, TypeAdapter<T> adapter) {
		this.gson = gson;
		this.adapter = adapter;
	}

	@Override
	public RequestBody convert(T value) throws IOException {
		// 加密
		// APIBodyData data = new APIBodyData();
		Log.i("xiaozhang", "request中传递的json数据：" + value.toString());
		// data.setData(XXTEA.Encrypt(value.toString(), HttpConstant.KEY));
		// String postBody = gson.toJson(data); //对象转化成json
		// Log.i("xiaozhang", "转化后的数据：" + postBody);
		// return RequestBody.create(MEDIA_TYPE, "");
		Buffer buffer = new Buffer();
		Writer writer = new OutputStreamWriter(buffer.outputStream(), UTF_8);
		JsonWriter jsonWriter = gson.newJsonWriter(writer);
		adapter.write(jsonWriter, value);
		ByteString byteStr = buffer.readByteString();
		jsonWriter.close();
		buffer.close();
		return RequestBody.create(MEDIA_TYPE, byteStr);
	}

}
