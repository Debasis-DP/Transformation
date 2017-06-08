<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="text"/>
    
    <xsl:template match="*">
    {
        "Order": 
        { <xsl:template match="header">
            "Header" : 
            {
                "Id" : <xsl:value-of select="header/order-id" />,
                "Price" : <xsl:value-of select = "header/net-amount" />,
                <xsl:template match="header/customer-details">
                "CustomerDetails" : 
                {
                    "UserName" : "<xsl:value-of select="header/customer-details/username" />",
                    "FirstName" : "<xsl:value-of select="header/customer-details/firstname" />",
                    "LastName" : "<xsl:value-of select="header/customer-details/lastname" />"
                }</xsl:template>
            },
            </xsl:template>
            <xsl:template match="order-item">
            "OrderItems" :
             [
             <xsl:for-each select="order-item/element">
                {
                    "Quantity" : <xsl:value-of select="quantity" />,
                    "ProductId" : "<xsl:value-of select="product-id" />",
                    "Title" : "<xsl:value-of select="title"/>",
                    "Price" : <xsl:value-of select = "price"/>
                }<xsl:if test="following-sibling::*">,</xsl:if>
             </xsl:for-each>
             ]   
            </xsl:template>
        }
    }         
    </xsl:template>
</xsl:stylesheet>
