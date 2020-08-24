package org.springframework.amqp.tutorials.rabbitmqamqptutorials;

import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.TypeBindings;
import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.classmate.types.ResolvedRecursiveType;
import io.vavr.collection.List;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.schema.AlternateTypeRule;


@Slf4j
public class RecursiveAlternateTypeRule extends AlternateTypeRule {

    private List<AlternateTypeRule> rules;

    RecursiveAlternateTypeRule(TypeResolver typeResolver, List<AlternateTypeRule> rules) {
        super(typeResolver.resolve(Object.class), typeResolver.resolve(Object.class));
        this.rules = rules;
    }

    @Override
    public ResolvedType alternateFor(ResolvedType type) {
        ResolvedType partiallyResolvedType = alternateForParams(type);

        ResolvedType newType = rules.flatMap(rule -> List.of(rule.alternateFor(partiallyResolvedType))
                .filter(alternateType -> alternateType != partiallyResolvedType))
                .getOrElse(type);

        if (appliesTo(newType)) {
            return alternateFor(newType);
        }
        return newType;
    }

    @Override
    public boolean appliesTo(ResolvedType type) {
        return rules.exists(rule -> rule.appliesTo(type));
    }

    private ResolvedType alternateForParams(ResolvedType type) {
        // If the type is not a generic one (i.e. type parameters list is empty), it is directly returned.
        if (type.getTypeParameters().isEmpty()) {
            return type;
        }
        // Otherwise, all its parameters have to be substituted (according to predefined rules).
        // Please note that this is a recursive operation (because of the use of 'alternateFor' method ahead).
        ResolvedType[] resolvedTypeParameters = type.getTypeParameters()
                .stream()
                .map(this::alternateFor)
                .toArray(ResolvedType[]::new);

        TypeBindings typeBindings = TypeBindings.create(type.getErasedType(), resolvedTypeParameters);
        return new ResolvedRecursiveType(type.getErasedType(), typeBindings);
    }

}