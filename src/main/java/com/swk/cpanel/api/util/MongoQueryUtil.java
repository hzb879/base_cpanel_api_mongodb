package com.swk.cpanel.api.util;

import java.util.Objects;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class MongoQueryUtil {
	
	public static QueryBuilder queryBuilder(Pageable pageable) {
		return new QueryBuilder(pageable);
	}
	
	public static class QueryBuilder{
		
		private Query query=null;
		
		public QueryBuilder(Pageable pageable) {
			this.query = new Query();
			query.with(pageable);
		}
		
		public QueryBuilder addIsCriteria(String key,Object value) {
			if(Objects.nonNull(value) && ValidatorUtil.notBlank(value.toString())) {
				query.addCriteria(Criteria.where(key).is(value));
			}
			return this;
		}
		
		public QueryBuilder addSimpleRegexCriteria(String key,String value) {
			if(ValidatorUtil.notBlank(value)) {
				query.addCriteria(Criteria.where(key).regex(value));
			}
			return this;
		}
		
		public Query build() {
			return this.query;
		}
	}
}
