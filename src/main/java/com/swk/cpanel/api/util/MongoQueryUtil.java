package com.swk.cpanel.api.util;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
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
		
		public QueryBuilder equals(String key, Object value) {
			if (Objects.nonNull(value) && StringUtils.isNotBlank(value.toString())) {
				query.addCriteria(Criteria.where(key).is(value));
			}
			return this;
		}
		
		public QueryBuilder notEquals(String key, Object value) {
			if (Objects.nonNull(value) && StringUtils.isNotBlank(value.toString())) {
				query.addCriteria(Criteria.where(key).ne(value));
			}
			return this;
		}
		
		public QueryBuilder regex(String key, String value) {
			if (StringUtils.isNotBlank(value)) {
				query.addCriteria(Criteria.where(key).regex(value));
			}
			return this;
		}
		
		public QueryBuilder lessThan(String key, Object value) {
			if(Objects.nonNull(value) && StringUtils.isNotBlank(value.toString())) {
				query.addCriteria(Criteria.where(key).lt(value));
			}
			return this;
		}
		
		public QueryBuilder LessThanEqual(String key, Object value) {
			if(Objects.nonNull(value) && StringUtils.isNotBlank(value.toString())) {
				query.addCriteria(Criteria.where(key).lte(value));
			}
			return this;
		}
		
		public QueryBuilder greaterThan(String key, Object value) {
			if(Objects.nonNull(value) && StringUtils.isNotBlank(value.toString())) {
				query.addCriteria(Criteria.where(key).gt(value));
			}
			return this;
		}
		
		public QueryBuilder greaterThanEqual(String key, Object value) {
			if(Objects.nonNull(value) && StringUtils.isNotBlank(value.toString())) {
				query.addCriteria(Criteria.where(key).gte(value));
			}
			return this;
		}
		
		public QueryBuilder between(String key, Object min, Object max) {
			if(Objects.nonNull(min) && Objects.nonNull(max)) {
				query.addCriteria(Criteria.where(key).gte(min).and(key).lte(max));
			}
			return this;
		}
		
		public Query build() {
			return this.query;
		}
	}
}
