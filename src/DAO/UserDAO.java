package DAO;


import Entity.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;


import java.util.ArrayList;
import java.util.List;
import java.util.Queue;


/**
 * Created by killeryuan on 2016/10/19.
 */
//@Transactional(readOnly = false)
public class UserDAO   implements IUserDAO{

    private int ok = 0;
    private int  failed = 1;
    private int  UnknowError = 2;

    private SessionFactory sessionFactory ;
    public void setsessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     *  login
     * @param userEntity
     * @return
     */
    public int login(UserEntity userEntity){
        try {
            Session session = sessionFactory.openSession();
            String hql = " from UserEntity  where phoneNumber = '" + userEntity.getPhoneNumber() + "'";
            Query query = session.createQuery(hql);
            List<UserEntity> users = query.list();
            session.close();
            if (users.size() == 0) return failed;
            else if (users.get(0).getPassword().equals(userEntity.getPassword())) {
                    userEntity.setName(users.get(0).getName());
                    return ok;
            } else return failed;
        }catch (Exception e){
            e.printStackTrace();
            return UnknowError;
        }
    }

    /**
     *  register
     * @param userEntity
     * @return
     */
    public int register(UserEntity userEntity){
        try {
            Session session = sessionFactory.openSession();
            String hql = "from UserEntity  where ( phoneNumber = '" + userEntity.getPhoneNumber() + "'or email = '"+userEntity.getEmail()+"')";
            Query query = session.createQuery(hql);
            if (query.list().size() != 0) return failed;
            session.getTransaction();
            session.save(userEntity);
            session.beginTransaction().commit();
            session.close();
            return ok;
        }catch (Exception e){
            e.printStackTrace();
            return UnknowError;
        }
    }

    /**
     *  finished medical record
     * @param medicalRecordEntity
     * @return
     */
    public int finishedMedical_record(MedicalRecordEntity medicalRecordEntity){
        try {
            Session session = sessionFactory.openSession();
            session.getTransaction();
            session.save(medicalRecordEntity);
            System.out.println(medicalRecordEntity.getWeight());
            session.beginTransaction().commit();
            session.close();
            return ok;
        }catch (Exception e){
            e.printStackTrace();
            return UnknowError;
        }
    }

    /**
     *   contacts login
     * @param phoneNumber
     * @param custodyCode
     * @return
     */
    public int contactsLogin(String phoneNumber, String custodyCode){
        try {
            Session session = sessionFactory.openSession();
            String hql = "select ue.name from UserEntity as ue, MedicalRecordEntity as mre where ue.phoneNumber = " +
                    " mre.phonenumber and ue.custodyCode = '" + custodyCode + "' and (mre.contactsNumber1 = '" + phoneNumber + "' or " +
                    " mre.contactsNumber2 = '" + phoneNumber + "' )";
            Query query = session.createQuery(hql);
            List list = query.list();
            session.close();
            if (list.size() != 0) return ok;
            else return failed;
        }catch ( Exception e){
            e.printStackTrace();
            return UnknowError;
        }
    }

    /**
     * add alertMessage
     * @param alertEntity
     * @return
     */
    public int addAlert(AlertEntity alertEntity){
        try {
            Session session = sessionFactory.openSession();
            session.getTransaction();
            session.save(alertEntity);
            session.beginTransaction().commit();
            session.close();
            return ok;
        }catch (Exception e){
            e.printStackTrace();
            return UnknowError;
        }
    }

    /**
     * echo AED
     * @param alertEntity
     * @return
     */
    public int echoAED(AlertEntity alertEntity){
        Session session = sessionFactory.openSession();
        try {
            String hql = "from AlertEntity where isCheck = '0'";
            Query query = session.createQuery(hql);
            List alerts = query.list();
            if (alerts.size() == 0) return failed;
            alertEntity = (AlertEntity) alerts.get(0);
            return ok;
        } catch (Exception e){
            e.printStackTrace();
            return UnknowError;
        } finally {
            alertEntity.setIsCheck("1");
            session.getTransaction();
            session.update(alertEntity);
            session.beginTransaction().commit();
            session.close();
        }
    }

    /**
     *  set JPush alias
     * @param jpushAliasEntity
     * @return
     */
    public int setAlias(JpushAliasEntity jpushAliasEntity){
        try {
            Session session = sessionFactory.openSession();
            session.getTransaction();
            session.save(jpushAliasEntity);
            session.beginTransaction().commit();
            session.close();
            return ok;
        }catch (Exception e){
            e.printStackTrace();
            return UnknowError;
        }
    }

    /**
     *  get Critical
     * @param phoneNumber
     * @return
     */
    public List<MedicalRecordEntity> getCritical(String phoneNumber){
        Session session = sessionFactory.openSession();
//        String hql = "select jae.alias ,mre.name from MedicalRecordEntity as mre, JpushAliasEntity as jae where mre.phonenumber = '"+phoneNumber+"'" +
//                " and ( mre.contactsNumber1 = jae.phoneNumber or  mre.contactsNumber2 = jae.phoneNumber) ";
        String hql = "from MedicalRecordEntity where phonenumber = '"+phoneNumber+"'";

        Query query = session.createQuery(hql);
        List<MedicalRecordEntity> list = query.list();
        //String[] alias = new String[list.size()];
        return list;
    }

    /**
     *  get user`s name
     * @param userEntity
     * @return
     */
    public int getRealName(UserEntity userEntity){
        try {
            Session session = sessionFactory.openSession();
            String hql = "from UserEntity where custodyCode = '" + userEntity.getCustodyCode() + "'";
            Query query = session.createQuery(hql);
            List<UserEntity> userEntities = query.list();
            userEntity.setName(userEntities.get(0).getName());
            session.close();
            return ok;
        }catch (Exception e){
            e.printStackTrace();
            return UnknowError;
        }
    }

    /**
     *  get all aed message
     * @return
     */
    public List<DeviceEntity> getAllAED(){
        Session session = sessionFactory.openSession();
        String hql = "from DeviceEntity ";
        Query query = session.createQuery(hql);
        List<DeviceEntity> list = query.list();
        return list;
    }

    /**
     * new
     */
    /**
     *
     * @param institutionEntity
     * @return
     */
    public int register(InstitutionEntity institutionEntity){
        Session session = sessionFactory.openSession();
        try{
            List list = new ArrayList()     ;
            if(create(institutionEntity, session, list)) {
                institutionEntity = (InstitutionEntity)list.get(0);
                System.out.println(institutionEntity.getInstitutionId());
                return ok;
            }
            else
                return failed;
        } catch (Exception e){
            e.printStackTrace();
            return UnknowError;
        }
    }

    /**
     *
     * @param institutionId
     * @param password
     * @return
     */
    public int instLogin(String institutionId, String password){
        System.out.println(institutionId);
        System.out.println(password);
        Session session = sessionFactory.openSession();
        try{
            InstitutionEntity institutionEntity =(InstitutionEntity) getObject(3,institutionId, session);
            if(password.equals(institutionEntity.getPassword()+"")){
                return ok;
            } else {
                return failed;
            }
        } catch (Exception e){
            return UnknowError;
        }
    }

    /**
     *
     * @param aidPeopleEntity
     * @return
     */
    public int register(AidPeopleEntity aidPeopleEntity){
        Session session = sessionFactory.openSession();
        Session session1 = sessionFactory.openSession();
        try{
            AidPeopleEntity aidPeopleEntity1 =(AidPeopleEntity) getObject(0, aidPeopleEntity.getPeopleAidId()+"", session);
            if(aidPeopleEntity1!=null){
                List o = new ArrayList();
                if(create( aidPeopleEntity, session1, o)) return ok;
                else return failed;
            } else return failed;
        } catch ( Exception e){
            e.printStackTrace();
            return UnknowError;
        }

    }

    /**
     *
     * @param aidPhoneNumber
     * @param password
     * @return
     */
    public int aidLogin(String aidPhoneNumber, String password){
        Session session = sessionFactory.openSession();
        try{
            String hql = "from AidPeopleEntity where aidPhoneNumber = '"+aidPhoneNumber+"'";
            AidPeopleEntity aidPeopleEntity =(AidPeopleEntity)queryObjectByHQL(hql, session);
            if ((aidPeopleEntity.getPassword()).equals(password))
                return ok;
            return failed;
        } catch (Exception e){
            return UnknowError;
        }
    }

    /**
     *
     * @param institutionId
     * @return
     */
    public List<AidPeopleEntity> getAidPeople(Integer institutionId){
        Session session = sessionFactory.openSession();
        try{
            String hql = "from AidPeopleEntity where institutionId = "+institutionId+"";
            List<AidPeopleEntity> list = (List)queryByHQL(hql, session);
            return list;
        } catch (Exception e){
            return new ArrayList<>();
        }
    }


    /**
     *
     * @param institutionId
     * @return
     */
    public List<DeviceEntity> getDevice(Integer institutionId){
        Session session = sessionFactory.openSession();
        try{
            String hql = "from DeviceEntity where institutionId = "+institutionId+"";
            List<DeviceEntity> list = (List)queryByHQL(hql, session);
            return list;
        } catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     *
     * @param deviceEntity
     * @return
     */
    public int addDevice(DeviceEntity deviceEntity){
        Session session = sessionFactory.openSession();
        try{
            List o = new ArrayList();
            if(create(deviceEntity, session, o))
                return ok;
            else
                return failed;
        } catch (Exception e){
            e.printStackTrace();
            return UnknowError;
        }
    }

    /**
     *
     * @param institutionId
     * @return
     */
    public List<AlertEntity> getAllAlert(Integer institutionId){
        Session session = sessionFactory.openSession();
        try{
            String  hql = "from AlertEntity  as ae, DeviceEntity as de where ae.deviceId = de.deviceId and de.institutionId = "+institutionId;
            List<AlertEntity> list = (List)queryByHQL(hql, session);
            return list;
        } catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     *
     * @param aidPhoneNumber
     * @return
     */
    public AidPeopleEntity getAidPeople(String aidPhoneNumber){
        Session session = sessionFactory.openSession();
        try{
            String hql = "from AidPeopleEntity where aidPhoneNumber = '"+aidPhoneNumber+"'";
            return  (AidPeopleEntity)queryObjectByHQL(hql, session);
        } catch (Exception e){
            e.printStackTrace();
            return new AidPeopleEntity();
        }
    }

    /**
     *
     * @param aidPhoneNumber
     * @param password
     * @return
     */
    public int confirmPassword(String aidPhoneNumber, String password){
        Session session = sessionFactory.openSession();
        try{
            String hql = "from AidPeopleEntity where aidPhoneNumber = '"+aidPhoneNumber+"'";
         //   Query query = session.createQuery(hql);
            AidPeopleEntity aidPeopleEntity = (AidPeopleEntity)queryObjectByHQL(hql, session);
            if(password.equals(aidPeopleEntity.getPassword()))
                return ok;
            return failed;

        } catch (Exception e){
            e.printStackTrace();
            return UnknowError;
        }
    }

    /**
     *
     * @param aidPhoneNumber
     * @param password
     * @return
     */
    public int changePassword(String aidPhoneNumber,String password){
        Session session = sessionFactory.openSession();
        Session session1 = sessionFactory.openSession();
        try{
            String hql = "from AidPeopleEntity where aidPhoneNumber = '"+aidPhoneNumber+"'";
            AidPeopleEntity aidPeopleEntity = (AidPeopleEntity)queryObjectByHQL(hql, session);
            aidPeopleEntity.setPassword(password);
            if(update(aidPeopleEntity, session1))
                return ok;
            return failed;
        } catch (Exception e) {
            e.printStackTrace();
            return UnknowError;
        }
    }

    /**
     *
     * @param aidPhoneNumber
     * @param institutionId
     * @return
     */
    public int changeInstitution(String aidPhoneNumber, int institutionId){
        Session session = sessionFactory.openSession();
        Session session1 = sessionFactory.openSession();
        try{
            String hql = "from AidPeopleEntity where aidPhoneNumber = '"+aidPhoneNumber+"'";
            AidPeopleEntity aidPeopleEntity = (AidPeopleEntity)queryObjectByHQL(hql, session);
            aidPeopleEntity.setInstitutionId(institutionId);
            if(update(aidPeopleEntity, session1))
                return ok;
            return failed;
        } catch (Exception e) {
            e.printStackTrace();
            return UnknowError;
        }
    }

    /**
     *
     * @param phoneNumber
     * @return
     */
    public String getBasicInfo(String phoneNumber){
        String name  ;
        String status = "正常";
        String heartRate="0";
        String recordCount ;
        Session session = sessionFactory.openSession();
        Session session1 = sessionFactory.openSession();
        Session session2 = sessionFactory.openSession();
        String hql = "from MedicalRecordEntity where ( contactsNumber1 = '"+phoneNumber+"' or contactsNumber2 = '"+phoneNumber+"')";
        MedicalRecordEntity medicalRecordEntity = (MedicalRecordEntity) queryObjectByHQL(hql, session);
        name = medicalRecordEntity.getName();
        String number = medicalRecordEntity.getPhonenumber();
     //   UserEntity userEntity = (UserEntity)getObject(6, number,session1);
        hql = "from LastRecvEntity where phoneNumber = '"+number+"'";
        LastRecvEntity lastRecvEntity = (LastRecvEntity)queryObjectByHQL(hql, session2);
        if(lastRecvEntity!=null) {
        //    status = lastRecvEntity.getStatus();
            heartRate = lastRecvEntity.getHeartRate();
        }
        hql = "select count(*) from AlertEntity where phoneNumber = '"+number+"'" ;
        Query query = session1.createQuery(hql);
        List<Integer> list = query.list();
        recordCount = list.get(0)+"";

        hql = "from AlertEntity where phoneNumber = '"+number+"'";
        Session session3 = sessionFactory.openSession();
        AlertEntity alertEntity = (AlertEntity)queryObjectByHQL(hql, session3);
        if(alertEntity!=null){
            status = alertEntity.getType();
        }

        return  "{\"name\":\""+name+"\",\"status\":\""+status+"\",\"heartRate\":\""+heartRate+"\",\"recordCount\":\""+recordCount+"\",\"phoneNumber\":\""+phoneNumber+"\"}";
    }

    public List<AlertEntity> getAllAlert(String phoneNumber){
        Session session = sessionFactory.openSession();
        String hql = "from MedicalRecordEntity  where  contactsNumber1 = '"+phoneNumber+"' or  contactsNumber2 = '"+phoneNumber+"'";
        MedicalRecordEntity medicalRecordEntity = (MedicalRecordEntity)queryObjectByHQL(hql, session);
        if(medicalRecordEntity!=null){
            String userPhoneNumber = medicalRecordEntity.getPhonenumber();
            hql =" from AlertEntity where phoneNumber = '"+userPhoneNumber+"'";
            Session session1 = sessionFactory.openSession();
            List<AlertEntity> list = (List)queryByHQL(hql, session1);
            return list;
        }
        return new ArrayList<>();
    }

    public int updateLocation(UserEntity userEntity){
        Session session = sessionFactory.openSession();
        try {
            String hql = "update UserEntity set locX = '" + userEntity.getLocX() + "' , locY = '" + userEntity.getLocY() + "' where phoneNumber = " + userEntity.getPhoneNumber();
            Query query = session.createQuery(hql);
            query.executeUpdate();
            return ok;
        } catch (Exception e){
            e.printStackTrace();
            return UnknowError;
        } finally {
            session.close();
        }
    }

    public int updateLocation(String locX, String locY, String phoneNumber){
        Session session = sessionFactory.openSession();
        try {

            String hql = "update UserEntity set locX = '" + locX + "' , set locY = '" + locY + "' where phoneNumber = '" + phoneNumber + "'";
            Query query = session.createQuery(hql);
            query.executeUpdate();
            return ok;
        }catch (Exception e){
            e.printStackTrace();
            return failed;
        } finally {
            session.close();
        }



    }

    public double[] getLocation(String phoneNumber){
        double[] doubles = new double[2];
        Session session = sessionFactory.openSession();
        String hql = "from MedicalRecordEntity where ( contactsNumber1 = '"+phoneNumber+"' or contactsNumber2 ='"+phoneNumber+"')";
        Query query = session.createQuery(hql);
        List<MedicalRecordEntity> medicals = query.list();
        if(medicals.size()==0){
            return null;
        } else {
            hql = "from UserEntity where phoneNumber = '"+medicals.get(0).getPhonenumber()+"'";
            query = session.createQuery(hql);
            List<UserEntity> user = query.list();
            if(user.size()==0){
                return null;
            } else {
                doubles[0] = Double.parseDouble( user.get(0).getLocX());
                doubles[1] = Double.parseDouble( user.get(0).getLocY());
                return doubles;
            }
        }
    }

    public int updateHeartRate(String phoneNumber, String heartRate){
        Session session = sessionFactory.openSession();
        Session session1 = sessionFactory.openSession();
        try {
            String q = "from LastRecvEntity where phoneNumber = '" + phoneNumber + "'";
            if (queryObjectByHQL(q, session1) != null) {
                String hql = "update LastRecvEntity set heartRate = '" + heartRate + "' where phoneNumber = '" + phoneNumber + "' ";
                Query query = session.createQuery(hql);
                query.executeUpdate();
            } else {
                LastRecvEntity lastRecvEntity = new LastRecvEntity();
                lastRecvEntity.setPhoneNumber(phoneNumber);
                lastRecvEntity.setHeartRate(heartRate);
                lastRecvEntity.setStatus("正常");
                session.getTransaction();
                session.save(lastRecvEntity);
                session.beginTransaction().commit();

            }
            return ok;
        }catch (Exception e){
            e.printStackTrace();
            return failed;
        }finally {
            session.close();
        }
    }

    public int getHeartRate(String phoneNumber){
        Session session = sessionFactory.openSession();
        String hql = "from MedicalRecordEntity where ( contactsNumber1 = '"+phoneNumber+"' or contactsNumber2 = '"+phoneNumber+"')";
        Query query = session.createQuery(hql);
        List<MedicalRecordEntity> list = query.list();
        if(list.size()==0)
            return 0;
        else {
            String userPhoneNumber = list.get(0).getPhonenumber();
            hql = "from LastRecvEntity where phoneNumber = '"+userPhoneNumber+"'";
            query = session.createQuery(hql);
            List<LastRecvEntity> list1 = query.list();
            if(list1.size()!=0){
                return Integer.parseInt(list1.get(0).getHeartRate());
            } else {
                return 0;
            }
        }
    }

    public double[] userGetLocation(String phoneNumber){
        Session session = sessionFactory.openSession();
        String hql = "from UserEntity where phoneNumber = '"+phoneNumber+"'";
        Query query = session.createQuery(hql);
        List<UserEntity> users = query.list();
        double[] locations = new double[2];
        locations[0] = Double.parseDouble(users.get(0).getLocX());
        locations[1] = Double.parseDouble(users.get(0).getLocY());
        session.close();
        return locations;
    }





//    public boolean create(Object Entity){
//        Session session = sessionFactory.openSession();
//        session.getTransaction();
//        session.save(Entity);
//        session.beginTransaction().commit();
//        return true;
//    }

    public boolean create(Object Entity, Session session, List object){
        try {
            session.getTransaction();
            session.save(Entity);
            object.add(Entity);
         //   InstitutionEntity in = (InstitutionEntity)Entity;
         //   System.out.println(in.getInstitutionId());
            session.beginTransaction().commit();
         //   InstitutionEntity ins = (InstitutionEntity)Entity;
       //     System.out.println(ins.getInstitutionId());
            return true;
        }catch ( Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Object Entity, Session session){
    try{
        session.getTransaction();
        session.update(Entity);
        session.beginTransaction().commit();;
        return true;
    }catch (Exception e){
        e.printStackTrace();
        return false;
    }finally {
        session.close();
    }
}

    public Object getObject(int index, String condition, Session session){
        try {
            String entities[] = {"AidPeopleEntity", "AlertEntity", "DeviceEntity", "InstitutionEntity", "JpushAliasEntity", "MedicalRecordEntity","UserEntity"};
            String Id[] = {"peopleAidId", "alertId", "deviceId", "institutionId", "jpushId", "phonenumber", "phoneNumber"};
            String ss[] = {"123", "2134"};
            String hql;
            if (index == 5 || index == 6) {
                String hql1 = "from  " + entities[index] + " where " ;
                String hql2 = Id[index] + "  =  '" + condition + "'";
                hql = hql1+hql2;
            }
            if(index==2){
                String hql1 = "from  " + entities[index] ;
                String hql2 = " where " + Id[index] + " = " + Integer.parseInt(condition);
                hql = hql1+hql2;
            }
            else {
               String hql1 = "from  " + entities[index] ;
                String hql2 = " where " + Id[index] + " = " + condition;
                hql = hql1+hql2;
            }
            Query query = session.createQuery(hql);
            List list = query.list();
            if (list.size() == 0)
                return null;
            return list.get(0);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    public List<Object> queryByHQL(String hql ,Session session ){
        try{
            Query query = session.createQuery(hql);
            List list = query.list();
            return list;
        } catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            session.close();
        }
    }

    public Object queryObjectByHQL(String hql ,Session session ){
        try{
            Query query = session.createQuery(hql);
            List list = query.list();
            if (list.size() == 0)
                return null;
            return list.get(0);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
}
