package de.digitalcollections.core.model.jackson.mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.digitalcollections.core.model.impl.paging.SortingImpl;

@JsonDeserialize(as = SortingImpl.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class SortingMixIn {

}
