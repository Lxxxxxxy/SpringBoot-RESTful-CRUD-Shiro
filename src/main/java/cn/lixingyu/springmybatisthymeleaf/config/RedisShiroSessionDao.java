package cn.lixingyu.springmybatisthymeleaf.config;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * @author Rlxy93
 * @time 2019/11/07 15:20
 */
@Component
public class RedisShiroSessionDao extends AbstractSessionDAO {

    @Autowired
    private RedisTemplate myRedisSessionTemplate;

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        myRedisSessionTemplate.opsForValue().set(session.getId(), session, 100, TimeUnit.SECONDS);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable serializable) {
        if (serializable != null) {
            return (Session) myRedisSessionTemplate.opsForValue().get(serializable);
        } else {
            return null;
        }
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        if (session != null && session.getId() != null) {
            session.setTimeout(100 * 1000);
            myRedisSessionTemplate.opsForValue().set(session.getId(), session, 100, TimeUnit.SECONDS);
        }
    }

    @Override
    public void delete(Session session) {
        if (session != null && session.getId() != null) {
            myRedisSessionTemplate.delete(session.getId());
        }
    }

    @Override
    public Collection<Session> getActiveSessions() {
        return myRedisSessionTemplate.keys("*");
    }
}
