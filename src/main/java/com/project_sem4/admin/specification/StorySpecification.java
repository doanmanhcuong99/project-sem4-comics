package com.project_sem4.admin.specification;


import com.project_sem4.admin.entity.Category;
import com.project_sem4.admin.entity.Story;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

public class StorySpecification implements Specification<Story> {
    private SearchCriteria criteria;

    public StorySpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }


    @Override
    public Predicate toPredicate(Root<Story> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder builder) {
        if (criteria.getOperation().equalsIgnoreCase(">=")) {
            return builder.greaterThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString());
        } else if (criteria.getOperation().equalsIgnoreCase("<=")) {
            return builder.lessThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString());
        } else if (criteria.getOperation().equalsIgnoreCase(":")) {
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                return builder.like(
                        root.get(criteria.getKey()), "%" + criteria.getValue() + "%");
            } else {
                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
            }
        } else if (criteria.getOperation().equalsIgnoreCase("joinCategory")) {
            Join<Story, Category> storyCategoryJoin = root.join("categories");
            Predicate predicate = builder.or(
                    builder.equal(storyCategoryJoin.get("id"), criteria.getValue())
            );
            return predicate;
        } else if (criteria.getOperation().equalsIgnoreCase("join")) {
            Join<Story, Category> storyCategoryJoin = root.join("categories");
            Predicate predicate = builder.or(
                    builder.like(root.get("title"), "%" + criteria.getValue() + "%"),
                    builder.like(root.get("description"), "%" + criteria.getValue() + "%"),
                    builder.like(root.get("director"), "%" + criteria.getValue() + "%"),
                    builder.like(storyCategoryJoin.get("name"), "%" + criteria.getValue() + "%")
            );
            return predicate;
        }
        return null;
    }
}

