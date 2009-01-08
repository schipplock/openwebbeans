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
package org.apache.webbeans.test.unittests;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.webbeans.SessionScoped;
import javax.webbeans.manager.Manager;

import junit.framework.Assert;

import org.apache.webbeans.component.AbstractComponent;
import org.apache.webbeans.container.ManagerImpl;
import org.apache.webbeans.context.ContextFactory;
import org.apache.webbeans.test.component.Singleton;
import org.apache.webbeans.test.component.service.ITyped2;
import org.apache.webbeans.test.component.service.Typed2;
import org.apache.webbeans.test.servlet.TestContext;
import org.junit.Before;
import org.junit.Test;

public class SingletonComponentTest extends TestContext
{
    Manager container = null;

    public SingletonComponentTest()
    {
        super(SingletonComponentTest.class.getSimpleName());
    }

    public void endTests(ServletContext ctx)
    {

    }

    @Before
    public void init()
    {
        super.init();
        this.container = ManagerImpl.getManager();
    }

    public void startTests(ServletContext ctx)
    {

    }

    @SuppressWarnings("unchecked")
    @Test
    public void testTypedComponent() throws Throwable
    {
        clear();

        defineSimpleWebBean(Typed2.class);
        defineSimpleWebBean(Singleton.class);
        List<AbstractComponent<?>> comps = getComponents();

        HttpSession session = getSession();

        ContextFactory.initSessionContext(session);

        Assert.assertEquals(2, comps.size());

        ManagerImpl.getManager().getContext(SessionScoped.class).get(comps.get(0), true);
        Object object = ManagerImpl.getManager().getContext(SessionScoped.class).get(comps.get(1), true);

        Assert.assertTrue(object instanceof Singleton);

        Singleton single = (Singleton) object;

        Assert.assertEquals("debug", single.logDebug());
        Assert.assertEquals("info", single.logInfoo());

        ITyped2 t = single.getType();

        Assert.assertNotNull(t);

        ContextFactory.destroySessionContext(session);
    }

}
