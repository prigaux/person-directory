/**
 * Copyright 2007 The JA-SIG Collaborative.  All rights reserved.
 * See license distributed with this file and
 * available online at http://www.uportal.org/license.html
 */
package org.jasig.services.persondir.support;

import java.util.List;
import java.util.Map;

/**
 * @author Eric Dalquist
 * @version $Revision$
 */
public class AttributeNamedPersonImpl extends BasePersonImpl {
    private static final long serialVersionUID = 1L;

    public static final String DEFAULT_USER_NAME_ATTRIBUTE = "username";
    
    private final String userNameAttribute;

    public AttributeNamedPersonImpl(Map<String, List<Object>> attributes) {
        super(attributes);
        
        this.userNameAttribute = DEFAULT_USER_NAME_ATTRIBUTE;
    }
    
    public AttributeNamedPersonImpl(String userNameAttribute, Map<String, List<Object>> attributes) {
        super(attributes);
        
        this.userNameAttribute = userNameAttribute;
    }

    /* (non-Javadoc)
     * @see java.security.Principal#getName()
     */
    public String getName() {
        final Object attributeValue = this.getAttributeValue(this.userNameAttribute);
        if (attributeValue == null) { 
            return null;
        }
        
        return attributeValue.toString();
    }
}