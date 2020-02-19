package chenqi.cms;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.chenqi.cms.dao.ArticleRes;
import com.chenqi.cms.domain.Article;
import com.chenqi.cms.service.ArticleService;
import com.github.pagehelper.PageInfo;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-beans.xml")
public class TestImportMysql {

	@Autowired
	ArticleRes articleRes;
	
	@Autowired
	ArticleService articleService;
	
	@Test
	public void testImpotMysql() {
		//1.从mysql中查询所有文章信息
		
		Article article = new Article();
		article.setStatus(1);
		PageInfo<Article> selects = articleService.selects(article, 1, 10000);
		//2.把查询出来的文章批量保存到es索引库
		articleRes.saveAll(selects.getList());
		
	}
	
}
