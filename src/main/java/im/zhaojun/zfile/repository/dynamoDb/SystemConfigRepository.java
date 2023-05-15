package im.zhaojun.zfile.repository.dynamoDb;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import im.zhaojun.zfile.model.entity.dynamoDb.SystemConfig;

//@Repository
@EnableScan //コンフィグレーションクラスの、@EnableDynamoDBRepositoriesに応じて、リポジトリクラスが自動実装されるようになります
public interface SystemConfigRepository extends CrudRepository<SystemConfig, String> {

	/**
	 * 查找系统设置中, 某个设置项对应的值
	 *
	 * @param   key
	 *          设置项
	 *
	 * @return  设置值
	 */
	//	@Query("select * from SystemConfig where key = :key")
	//	List<SystemConfig> findByKey(String key);

}
