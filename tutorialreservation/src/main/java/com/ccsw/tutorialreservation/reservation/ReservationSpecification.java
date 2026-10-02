package com.ccsw.tutorialreservation.reservation;

import com.ccsw.tutorialreservation.common.criteria.SearchCriteria;
import com.ccsw.tutorialreservation.reservation.model.Reservation;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class ReservationSpecification implements Specification<Reservation> {

    private static final long serialVersionUID = 1L;

    private final SearchCriteria criteria;

    public ReservationSpecification(SearchCriteria criteria) {

        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Reservation> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (criteria.getValue() != null) {
            Path<?> path = getPath(root);
            if (criteria.getOperation().equalsIgnoreCase(":")) {
                if (path.getJavaType() == String.class) {
                    return builder.like(builder.lower(path.as(String.class)), "%" + criteria.getValue().toString().toLowerCase() + "%");
                } else {
                    return builder.equal(path, criteria.getValue());
                }
            } else if (criteria.getOperation().equalsIgnoreCase(">=")) {
                return builder.greaterThanOrEqualTo(path.as(LocalDate.class), (LocalDate) criteria.getValue());
            } else if (criteria.getOperation().equalsIgnoreCase("<=")) {
                return builder.lessThanOrEqualTo(path.as(LocalDate.class), (LocalDate) criteria.getValue());
            }
        }
        return null;
    }

    private Path<?> getPath(Root<Reservation> root) {
        String key = criteria.getKey();

        if ("game.id".equals(key)) {
            return root.get("idGame");
        }

        if ("client.id".equals(key)) {
            return root.get("idClient");
        }
        String[] split = key.split("[.]", 0);

        Path<?> expression = root.get(split[0]);
        for (int i = 1; i < split.length; i++) {
            expression = expression.get(split[i]);
        }

        return expression;
    }

}
