package chenqi.cms;

import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.chenqi.cms.dao.ArticleMapper;
import com.chenqi.cms.domain.ArticleWithBLOBs;
import com.chenqi.cms.test.FileUtils;
import com.chenqi.utils.FileUtil;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:producer.xml")
public class TestSend {

	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;
	
	
	
	@Test
	public void testSend() throws Exception {
		File file = new File("d:/1708D");
		File[] files = file.listFiles();
		for (File file2 : files) {
			String title = file2.getName().replace(".txt", "");
			String readFile = FileUtils.readFile(file2, "utf8");
			ArticleWithBLOBs awb = new ArticleWithBLOBs();
			awb.setTitle(title);
			awb.setContent(readFile);
			String jsonString = JSON.toJSONString(awb);
			kafkaTemplate.send("1708D",jsonString);
			
		}
		
	}
	
	
}
