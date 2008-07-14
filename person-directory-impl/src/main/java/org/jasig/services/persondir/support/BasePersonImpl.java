/**
 * Copyright 2007 The JA-SIG Collaborative.  All rights reserved.
 * See license distributed with this file and
 * available online at http://www.uportal.org/license.html
 */
package org.jasig.services.persondir.support;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.jasig.services.persondir.IPerson;

/**
 * @author Eric Dalquist
 * @version $Revision$
 */
public abstract class BasePersonImpl implements IPerson {
    private final Map<String, List<Object>> attributes;
    
    public BasePersonImpl(Map<String, List<Object>> attributes) {
        Validate.notNull(attributes, "attributes can not be null");
        
        final Map<String, List<Object>> immutableValuesBuilder = this.buildImmutableAttributeMap(attributes);
        
        this.attributes = Collections.unmodifiableMap(immutableValuesBuilder);
    }

    /**
     * Take the constructor argument and convert the Map and List values into read-only form
     */
    protected Map<String, List<Object>> buildImmutableAttributeMap(Map<String, List<Object>> attributes) {
        final Map<String, List<Object>> immutableValuesBuilder = this.createImmutableAttributeMap(attributes.size());

        for (final Map.Entry<String, List<Object>> attrEntry : attributes.entrySet()) {
            final String key = attrEntry.getKey();
            List<Object> value = attrEntry.getValue();
            
            if (value != null) {
                value = Collections.unmodifiableList(value);
            }
            
            immutableValuesBuilder.put(key, value);
        }

        return immutableValuesBuilder;
    }

    /**
     * Create the Map used to store the attributes internally for this IPerson
     */
    protected Map<String, List<Object>> createImmutableAttributeMap(int size) {
        return new LinkedHashMap<String, List<Object>>(size > 0 ? size : 1);
    }

    /* (non-Javadoc)
     * @see org.jasig.services.persondir.IPerson#getAttributeValue(java.lang.String)
     */
    public Object getAttributeValue(String name) {
        final List<Object> values = this.attributes.get(name);
        if (values == null || values.size() == 0) {
            return null;
        }
        
        return values.get(0);
    }

    /* (non-Javadoc)
     * @see org.jasig.services.persondir.IPerson#getAttributeValues(java.lang.String)
     */
    public List<Object> getAttributeValues(String name) {
        final List<Object> values = this.attributes.get(name);
        if (values == null) {
            return null;
        }
        
        return values;
    }

    /* (non-Javadoc)
     * @see org.jasig.services.persondir.IPerson#getAttributes()
     */
    public Map<String, List<Object>> getAttributes() {
        return this.attributes;
    }

    /**
     * @see java.lang.Object#equals(Object)
     */
    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof IPerson)) {
            return false;
        }
        IPerson rhs = (IPerson) object;
        return new EqualsBuilder()
            .append(this.getName(), rhs.getName())
            .isEquals();
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(1574945487, 827742191)
            .append(this.getName())
            .toHashCode();
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("name", this.getName())
            .append("attributes", this.attributes)
            .toString();
    }
}