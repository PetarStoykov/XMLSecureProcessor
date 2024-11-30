<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:template match="/">
        <html>
            <body>
                <h2>Bookshop Catalog</h2>
                <ul>
                    <xsl:for-each select="bookshop/book">
                        <li>
                            <strong><xsl:value-of select="title"/></strong>
                            by <xsl:value-of select="author"/>
                            <br/>Price: <xsl:value-of select="price"/>
                            <br/>Availability: <xsl:value-of select="availability"/>
                        </li>
                    </xsl:for-each>
                </ul>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>