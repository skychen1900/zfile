package im.zhaojun.zfile.repository.dynamoDb;

import java.util.HashMap;
import java.util.Objects;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import im.zhaojun.zfile.model.entity.dynamoDb.SystemConfig;

/**
 * Spring Data DynamoDBにサポートしていないスキャンを実現<br/>
 * <br/>
 * https://docs.aws.amazon.com/ja_jp/amazondynamodb/latest/developerguide/DynamoDBMapper.Methods.html
 */
@Repository
@Component
public class SystemConfigRepositoryPlugin {
	private final DynamoDBMapper mapper;

	public SystemConfigRepositoryPlugin(DynamoDBMapper mapper) {
		this.mapper = mapper;
	}

	public SystemConfig findByKey(String value) {
		return this.findByKey("keyName", value);
	}

	public SystemConfig findByKey(String keyName, String keyValue) {
		Objects.nonNull(keyName);
		Objects.nonNull(keyValue);

		HashMap<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
		eav.put(":v1", new AttributeValue().withS(keyValue));

		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
				//.withFilterExpression("begins_with(" + keyName + ",:v1)")
				.withFilterExpression("begins_with(" + keyName + ",:v1)")
				.withExpressionAttributeValues(eav);

		return mapper.scan(SystemConfig.class, scanExpression)
				.get(0);

	}

	/*
	 * データ書き込む
	 */
	public void putRecord(String jsonFilePath, String tableName) {
		// TODO 
		// https://docs.aws.amazon.com/ja_jp/amazondynamodb/latest/developerguide/GettingStarted.WriteItem.html
	}

}
