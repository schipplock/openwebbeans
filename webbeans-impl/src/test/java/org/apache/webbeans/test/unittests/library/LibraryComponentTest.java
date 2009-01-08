/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with this
 * work for additional information regarding copyright ownership. The ASF
 * licenses this file to You under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.apache.webbeans.test.unittests.library;

import java.util.List;

import javax.servlet.ServletContext;

import junit.framework.Assert;

import org.apache.webbeans.component.AbstractComponent;
import org.apache.webbeans.context.ContextFactory;
import org.apache.webbeans.test.component.library.Book;
import org.apache.webbeans.test.component.library.BookShop;
import org.apache.webbeans.test.component.library.Shop;
import org.apache.webbeans.test.servlet.TestContext;
import org.junit.Before;
import org.junit.Test;

public class LibraryComponentTest extends TestContext
{
    public LibraryComponentTest()
    {
        super(LibraryComponentTest.class.getSimpleName());
    }

    public void endTests(ServletContext ctx)
    {

    }

    @Before
    public void init()
    {
        super.init();
    }

    public void startTests(ServletContext ctx)
    {

    }

    @Test
    public void testTypedComponent() throws Throwable
    {
        clear();

        defineSimpleWebBean(BookShop.class);
        List<AbstractComponent<?>> comps = getComponents();

        ContextFactory.initRequestContext(null);

        Assert.assertEquals(1, comps.size());

        AbstractComponent<?> obj = comps.get(0);

        Object instance = getManager().getInstance(obj);
        Assert.assertTrue(instance instanceof Shop);

        @SuppressWarnings("unchecked")
        Shop<Book> shop = (Shop<Book>) instance;
        shop.shop();

        ContextFactory.destroyRequestContext(null);
    }

}
