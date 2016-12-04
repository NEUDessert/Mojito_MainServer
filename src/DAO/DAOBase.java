package DAO;

import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * Created by killeryuan on 2016/11/15.
 */
public abstract class DAOBase extends HibernateDaoSupport {

    private Open open;

    public void setOpen(Open open) {
        this.open = open;
    }

    // 通过注解将sessionFactory注入到setSuperHibernateDaoSupport的参数中
    @Resource(name = "sessionFactory")
    public void setSuperHibernateDaoSupport(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    /**
     * 创建一个对象
     *
     * @param entity
     */
        public void create(Object entity) {
        try {
            getHibernateTemplate().save(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新一个对象
     *
     * @param entity
     */
    public void update(Object entity) {
        try {
            getHibernateTemplate().update(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除一个对象
     *
     * @param entity
     */
    public void delete(Object entity) {
        try {
            getHibernateTemplate().delete(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过条件查找单个对象
     *
     * @param clazz
     * @param key
     * @param val
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    protected Object loadByKey(final Class clazz, final String key, final Object val) {
        return getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException {
                Criteria criteria = session.createCriteria(clazz, "clazz");
                criteria.add(Restrictions.eq("clazz." + key, val));
                List result = criteria.list();
                if (result != null && result.size() > 0) {
                    return result.get(0);
                } else {
                    return null;
                }
            }
        });
    }

    @SuppressWarnings("rawtypes")
    protected List loadAll(Class clazz) {
        List result = getHibernateTemplate().find("from " + clazz.getName());
        if (result != null && result.size() > 0) {
            return result;
        } else {
            return null;

        }
    }

    /**
     * @function 根据HQL查询结果集
     * @version 2015-3-11 23:20:16
     */
    @SuppressWarnings("rawtypes")
    protected List queryByHQL(String hql) {
        List result = getHibernateTemplate().find(hql);
        if (result != null && result.size() > 0) {
            return result;
        } else {
            return null;

        }
    }

    /**
     * @function 根据HQL查询结果集，带分页效果
     * @version 2015-3-11 23:19:55
     */
    @SuppressWarnings("rawtypes")
    public List queryPageByHQL(final String hql) {
        @SuppressWarnings("unchecked")
        List result = (List) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Query query = session.createQuery(hql);
                query.setFirstResult(0);
                query.setMaxResults(5);
                List<Objects> list = query.list();
                return list;
            }
        });

        if (result != null && result.size() > 0) {
            return result;
        } else {
            return null;
        }
    }

    /**
     * @function 根据单一条件返回查询结果集
     * @version 2012-7-13 13:34:35
     */
    @SuppressWarnings("rawtypes")
    protected List loadAllByKey(Class clazz, String keyName, Object keyValue) {
        List result = getHibernateTemplate().find(
                "from " + clazz.getName() + " clazz" + " where " + "clazz."
                        + keyName + "=" + "'" + keyValue + "'");
        if (result != null && result.size() > 0) {
            return result;

        } else {
            return null;
        }
    }

    /**
     * @function 删除所有对象
     * @version 2012-7-13 13:34:35
     */
    @SuppressWarnings("rawtypes")
    protected void deleteAll(Class clazz) {
        getHibernateTemplate().deleteAll(
                getHibernateTemplate().find("from " + clazz.getName()));
    }

    /**
     * @function 删除所有对象
     * @version 2012-7-13 13:34:35
     */
    @SuppressWarnings("rawtypes")
    protected void deleteAllList(List list) {
        getHibernateTemplate().deleteAll(list);
    }
}
