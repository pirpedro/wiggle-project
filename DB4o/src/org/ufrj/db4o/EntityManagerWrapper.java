package org.ufrj.db4o;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.xml.bind.JAXBException;

import org.ufrj.db4o.exception.InvalidLoginException;
import org.ufrj.db4o.wrapper.DB4oServerWrapper;

import com.db4o.ObjectContainer;
import com.db4o.ext.DatabaseClosedException;
import com.db4o.ext.DatabaseReadOnlyException;
import com.db4o.ext.Db4oIOException;

public class EntityManagerWrapper implements EntityManager{

	private final static String ARQUIVO_CONFIGURACAO = "arquivos/Config.xml";
	
	ObjectContainer objectContainer;
	Configuracao configXML;
	
	EntityManagerWrapper() {
		
		
		configXML = recuperarConfiguracaoXML();
		if(configXML==null){
			throw new PersistenceException("N�o foi poss�vel encontrar config.xml");
		}
		inicializaObjectContainer();
		
	}
	
	private void inicializaObjectContainer(){
		if(objectContainer == null){
			try {
				objectContainer = DB4oServerWrapper.openClient(configXML.getHostName(), configXML.getPort(), configXML.getUsuario(), configXML.getSenha());
			} catch (InvalidLoginException e) {
				throw new PersistenceException("Usu�rio ou senah inv�lido",e);
				
			}
		}
	}
	
	@Override
	public void clear() {
		if(objectContainer!=null){
			objectContainer.close();
		}
		objectContainer=null;
		inicializaObjectContainer();
	}

	@Override
	public void close() {
		if(objectContainer!=null){
			
			try{
				objectContainer.close();
			}catch(Db4oIOException e){
				throw new IllegalStateException();
			}
		}
	}

	@Override
	public boolean contains(Object arg0) {
		throw new UnsupportedOperationException("Opera��o n�o � suportada por db4o", null);
	}

	@Override
	public Query createNamedQuery(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query createNativeQuery(String arg0) {
		throw new UnsupportedOperationException("Opera��o n�o � suportada por db4o", null);
	}

	@Override
	public Query createNativeQuery(String arg0, Class arg1) {
		throw new UnsupportedOperationException("Opera��o n�o � suportada por db4o", null);
	}

	@Override
	public Query createNativeQuery(String arg0, String arg1) {
		throw new UnsupportedOperationException("Opera��o n�o � suportada por db4o", null);
	}

	@Override
	public Query createQuery(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T find(Class<T> arg0, Object arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void flush() {
		
		try{
			getObjectContainer().commit();
		}catch(Db4oIOException e){
			throw new IllegalStateException(e);
		}catch(DatabaseClosedException e){
			throw new IllegalStateException(e);
		}catch(DatabaseReadOnlyException e){
			throw new IllegalStateException(e);
		}
		
	}

	@Override
	public Object getDelegate() {
		return getObjectContainer();
	}

	@Override
	public FlushModeType getFlushMode() {
		throw new UnsupportedOperationException("Opera��o n�o � suportada por db4o", null);
		
	}

	@Override
	public <T> T getReference(Class<T> arg0, Object arg1) {
		return find(arg0, arg1);
	}

	@Override
	public EntityTransaction getTransaction() {
		throw new UnsupportedOperationException("Opera��o n�o � suportada por db4o", null);
		
	}

	@Override
	public boolean isOpen() {
		
		return getObjectContainer()!=null;
	}

	@Override
	public void joinTransaction() {
		throw new UnsupportedOperationException("Opera��o n�o � suportada por db4o", null);
		
	}

	@Override
	public void lock(Object arg0, LockModeType arg1) {
		throw new UnsupportedOperationException("Opera��o n�o � suportada por db4o", null);
		
	}

	@Override
	public <T> T merge(T arg0) {
		//TODO 
		return null;
	}

	@Override
	public void persist(Object arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refresh(Object arg0) {
		getObjectContainer().ext().refresh(arg0, 1);
		
	}

	@Override
	public void remove(Object arg0) {
		
		try{
		getObjectContainer().delete(arg0);
		}catch(Db4oIOException e){
			throw new IllegalArgumentException(e);
		}catch(DatabaseClosedException e){
			throw new IllegalStateException(e);
		}catch(DatabaseReadOnlyException e){
			throw new IllegalStateException(e);
		}
	}

	@Override
	public void setFlushMode(FlushModeType arg0) {
		throw new UnsupportedOperationException("Opera��o n�o � suportada por db4o", null);
	}
	
	private ObjectContainer getObjectContainer(){
		inicializaObjectContainer();
		return objectContainer;
	}
	
	/**
	 * M�todo que recupera os dados do arquivo xml de configura��o do servidor.
	 * @return
	 */
	private static Configuracao recuperarConfiguracaoXML() {
		try {
			return XMLFactory.unmarshal(Configuracao.class, new FileInputStream( ARQUIVO_CONFIGURACAO ));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}

}
