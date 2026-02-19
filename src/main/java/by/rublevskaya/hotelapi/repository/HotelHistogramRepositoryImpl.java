package by.rublevskaya.hotelapi.repository;

import by.rublevskaya.hotelapi.model.Hotel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class HotelHistogramRepositoryImpl implements HotelHistogramRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Map<String, Long> getHistogramByParam(String param) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = cb.createTupleQuery();
        Root<Hotel> root = query.from(Hotel.class);

        Expression<String> groupExpression;

        switch (param.toLowerCase()) {
            case "brand" -> groupExpression = root.get("brand");
            case "city" -> groupExpression = root.get("address").get("city");
            case "country" -> groupExpression = root.get("address").get("country");
            case "amenities" -> groupExpression = root.join("amenities");
            default -> throw new IllegalArgumentException("Invalid histogram parameter: " + param);
        }

        query.multiselect(groupExpression.alias("paramName"), cb.count(root).alias("paramCount"));
        query.groupBy(groupExpression);

        List<Tuple> results = entityManager.createQuery(query).getResultList();
        Map<String, Long> histogram = new HashMap<>();

        for (Tuple tuple : results) {
            String key = tuple.get("paramName", String.class);
            Long count = tuple.get("paramCount", Long.class);
            histogram.put(key == null ? "Unknown" : key, count);
        }

        return histogram;
    }
}