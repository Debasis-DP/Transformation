<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="text"/>
    
    <xsl:template match="Customers">{
        "Customers": 
        { 
            "Customer" : 
            [
            <xsl:for-each select="Customer">
                {
                    "id" : <xsl:value-of select="@Id * 100" />,
                    "name" : "<xsl:value-of select="Name" />",
                    "age" : <xsl:value-of select="Age"/>,
                    "price" : <xsl:value-of select = "Price * 100" />,
                    "Address" : 
                    {
                        "Street" : "<xsl:value-of select="Address/Street" />",
                        "City" : "<xsl:value-of select="Address/City" />",
                        "Postal Code" : "<xsl:value-of select="Address/PostalCode" />"
                    }
                }<xsl:if test="following-sibling::*">,</xsl:if>
            </xsl:for-each>    
            ]
       }
} 
        
    </xsl:template>
</xsl:stylesheet>
