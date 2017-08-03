package de.digitalcollections.core.model.jackson.mixin;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.digitalcollections.core.model.impl.paging.OrderImpl;

@JsonDeserialize(as = OrderImpl.class)
public abstract class OrderMixIn {

}
