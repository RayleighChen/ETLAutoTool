package com.ray.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import org.tmatesoft.svn.core.ISVNDirEntryHandler;
import org.tmatesoft.svn.core.SVNCommitInfo;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNInfo;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNStatus;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

public class SVNUtil {
	private static SVNUtil svnTool = new SVNUtil();

	public static SVNUtil getInstance() {
		return svnTool;
	}

	private SVNUtil() {
		Properties props = new Properties();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream("res/SVN.properties");
			props.load(fis);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static SVNClientManager getClientManager() {
		FSRepositoryFactory.setup();

		Properties props = new Properties();
		FileInputStream fis = null;
		SVNRepository repository = null;

		try {
			fis = new FileInputStream("res/SVN.properties");
			try {
				props.load(fis);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			repository = FSRepositoryFactory.create(SVNURL
					.parseURIEncoded(props.getProperty("svnroot")));
		} catch (SVNException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ISVNAuthenticationManager authManager = SVNWCUtil
				.createDefaultAuthenticationManager(props
						.getProperty("username"), props.getProperty("password"));

		repository.setAuthenticationManager(authManager);

		DefaultSVNOptions options = SVNWCUtil.createDefaultOptions(true);
		return SVNClientManager.newInstance(options, authManager);

	}

	public static boolean isExist(SVNURL url, String username, String password) {
		SVNRepository svnRepository;
		try {
			svnRepository = SVNRepositoryFactory.create(url);
			ISVNAuthenticationManager authManager = SVNWCUtil
					.createDefaultAuthenticationManager(username, password);
			svnRepository.setAuthenticationManager(authManager);
			SVNNodeKind nodeKind = svnRepository.checkPath("", -1);
			return nodeKind == SVNNodeKind.NONE ? false : true;
		} catch (SVNException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public static String[] list(String filePath) {
		File file = new File(filePath);

		DirEntryHandler handler = new DirEntryHandler();
		try {
			getClientManager().getLogClient().doList(file, SVNRevision.BASE,
					SVNRevision.BASE, true, SVNDepth.IMMEDIATES,
					SVNDirEntry.DIRENT_ALL, handler);
			return handler.getFilesName();
		} catch (SVNException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private static class DirEntryHandler implements ISVNDirEntryHandler {
		ArrayList<String> fileNameList = new ArrayList<String>();

		@Override
		public void handleDirEntry(SVNDirEntry dirEntry) throws SVNException {
			// TODO Auto-generated method stub
			fileNameList.add(dirEntry.getName());
		}

		public String[] getFilesName() {
			String[] filesName = new String[fileNameList.size()];
			for (int i = 0; i < fileNameList.size(); i++) {
				filesName[i] = fileNameList.get(i);
			}
			return filesName;
		}
	}

	public static long update(String filePath, SVNRevision updateToRevision,
			SVNDepth depth) {
		SVNUpdateClient updateClient = getClientManager().getUpdateClient();
		File file = new File(filePath);

		updateClient.setIgnoreExternals(false);

		try {
			return updateClient.doUpdate(file, updateToRevision, depth, false,
					false);
		} catch (SVNException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public static void add(String filePath) {
		File file = new File(filePath);
		try {
			getClientManager().getWCClient().doAdd(new File[] { file }, true,
					false, false, SVNDepth.INFINITY, false, false, true);
		} catch (SVNException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void delete(String filePath) {
		File file = new File(filePath);
		try {
			getClientManager().getWCClient().doDelete(file, true, true);
		} catch (SVNException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String info(String filePath) {
		SVNInfo info = null;
		File file = new File(filePath);
		try {
			info = getClientManager().getWCClient().doInfo(file,
					SVNRevision.HEAD);
		} catch (SVNException e) {
			e.printStackTrace();
		}
		return info.getCommittedRevision().toString();
	}

	public static boolean lock(String filePath, String lockMessage) {
		File[] files = new File[1];
		files[0] = new File(filePath);
		for (int i = 0; i < files.length; i++) {
			SVNStatus status = null;
			status = status(filePath, false);
			if (status.getLocalLock() == null) {
				try {
					getClientManager().getWCClient().doLock(files, true,
							lockMessage);
					System.out.println("Locked: " + files[i]);
				} catch (SVNException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return true;
			}
		}
		return false;
	}

	public static boolean unlock(String filePath) {
		File[] files = new File[1];
		files[0] = new File(filePath);
		FileInputStream nameInputStream = null;

		try {
			nameInputStream = new FileInputStream("res/SVN.properties");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Properties nameProperties = new Properties();
		try {
			nameProperties.load(nameInputStream);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		for (int i = 0; i < files.length; i++) {
			SVNInfo info = null;
			try {
				info = getClientManager().getWCClient().doInfo(files[i],
						SVNRevision.HEAD);
			} catch (SVNException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (info.getLock() != null
					&& info.getLock().getOwner().toString().equalsIgnoreCase(
							nameProperties.getProperty("username").toString())) {
				// System.out.println("UnLocked");
				try {
					getClientManager().getWCClient().doUnlock(files, true);
					System.out.println("Unlock: " + files[i]);
					return true;
				} catch (SVNException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	public static SVNStatus status(String filePath, boolean remote) {
		File file = new File(filePath);
		SVNStatus status = null;
		try {
			status = getClientManager().getStatusClient()
					.doStatus(file, remote);
		} catch (SVNException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}

	public static SVNCommitInfo commit(String filePath, boolean keepLocks,
			String commitMessage) {
		File file = new File(filePath);
		try {
			return getClientManager().getCommitClient().doCommit(
					new File[] { file }, keepLocks, commitMessage, null, null,
					false, false, SVNDepth.INFINITY);
		} catch (SVNException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static SVNCommitInfo mkdir(SVNURL url, String commitMessage) {
		try {
			return getClientManager().getCommitClient().doMkDir(
					new SVNURL[] { url }, commitMessage);
		} catch (SVNException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
