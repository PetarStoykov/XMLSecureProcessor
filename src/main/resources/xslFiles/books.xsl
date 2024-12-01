<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <!-- Match the root element and apply templates to its children -->
    <xsl:template match="/">
        <html>
            <head>
                <title>Bookshop Inventory</title>
                <style>
                    table {
                    width: 100%;
                    border-collapse: collapse;
                    }
                    th, td {
                    padding: 8px;
                    border: 1px solid #ddd;
                    }
                    th {
                    background-color: #f2f2f2;
                    }
                </style>
            </head>
            <body>
                <h2>Bookshop Inventory</h2>
                <table>
                    <thead>
                        <tr>
                            <th>Title</th>
                            <th>Author</th>
                            <th>Genre</th>
                            <th>Price</th>
                            <th>Availability</th>
                        </tr>
                    </thead>
                    <tbody>
                        <!-- Apply template to each book -->
                        <xsl:for-each select="bookshop/book">
                            <tr>
                                <td><xsl:value-of select="title"/></td>
                                <td><xsl:value-of select="author"/></td>
                                <td><xsl:value-of select="genre"/></td>
                                <td>
                                    <xsl:value-of select="price"/>
                                    <xsl:text> </xsl:text>
                                    <xsl:value-of select="price/@currency"/>
                                </td>
                                <td><xsl:value-of select="availability"/></td>
                            </tr>
                        </xsl:for-each>
                    </tbody>
                </table>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>