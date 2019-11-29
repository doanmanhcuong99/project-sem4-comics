package com.project_sem4.admin.specification;

import com.project_sem4.admin.entity.Account;
import com.project_sem4.admin.entity.Story;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

public class AccountSpecification implements Specification<Account> {
    private SearchCriteria criteria;

    public AccountSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }


    @Override
    public Predicate toPredicate(Root<Account> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder builder) {
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
        } else if (criteria.getOperation().equalsIgnoreCase("join")) {
            Join<Account, Story> accountStoryJoin = root.join("stories");
            Predicate predicate = builder.or(
                    builder.like(root.get("email"), "%" + criteria.getValue() + "%"),
                    builder.like(root.get("fullname"), "%" + criteria.getValue() + "%"),
                    builder.like(root.get("address"), "%" + criteria.getValue() + "%"),
                    builder.like(accountStoryJoin.get("title"), "%" + criteria.getValue() + "%")
            );
            return predicate;
        }
        return null;
    }
}
