package com.example.demo;

import com.blazebit.persistence.PagedList;
import com.blazebit.persistence.querydsl.BlazeJPAQuery;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ImageService {

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    public PagedList<Image> find(){
        QImage image = QImage.image;


        Map<String, Expression<? extends Comparable<?>>> bindings = Map.of(
                "id", image.id);

//            ordering = new OrderSpecifier[]{new OrderSpecifier<>(Order.ASC,
//                    Expressions.template(Float.class, "l2_distance({0}, {1})", image.embedding, vectors))};
            var predicateWithoutNullEmbeddings = predicate.and(image.embedding.isNotNull());
            // OrderSpecifier<?>[] orderSpecifiers = new OrderSpecifier<?>[ordering.length];
            List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
            orderSpecifiers.add(new OrderSpecifier<>(Order.ASC, Expressions.path(Float.class,"distance")));
            Collections.addAll(orderSpecifiers, ordering);
            var tmp = new BlazeJPAQuery<>(entityManager,customJPQLNextTemplates, criteriaBuilderFactory)

                    .from(image)
                    .where(predicateWithoutNullEmbeddings)
                    .orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]));

//            float[] vectors = new float[]{};
//            new BlazeJPAQuery<>(entityManager,customJPQLNextTemplates, criteriaBuilderFactory)
//                    .select(Projections.constructor(ImageSearchDto.class,
//                                    image.id,
//                                    image.path,
//                                    image.starred,
//                                    Projections.constructor(ImageUrlsDto.class,
//                                            image.sourceSmall,
//                                            image.sourceMedium,
//                                            image.sourceLarge)))
//                    .from(image)
//                    .where(predicate)
//                    .orderBy(new OrderSpecifier<>(Order.ASC, Expressions.template(Float.class, "l2_distance({0}, {1})", image.embedding, vectors)))
//                    .fetchPage((int) pageable.getOffset(), pageable.getPageSize());

            // log.info(tmp.toString());
            // return new PagedArrayList<>(tmp.stream().map(tuple -> tuple.get(1, ImageSearchDto.class)).collect(Collectors.toList()), null, tmp.getTotalSize(), tmp.getFirstResult(), tmp.getMaxResults());
            return tmp.fetchPage((int) pageable.getOffset(), pageable.getPageSize());
    }
}
