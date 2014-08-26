/**
 *
 */
package de.uni_stuttgart.iste.cowolf.model.ctmc.analyze;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Map.Entry;

import javax.swing.JOptionPane;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.emf.ecore.resource.Resource;

import de.uni_stuttgart.iste.cowolf.model.IAnalysisListener;
import de.uni_stuttgart.iste.cowolf.model.ctmc.State;
import de.uni_stuttgart.iste.cowolf.model.ctmc.analyze.CTMCAnalyzeJob.Result;

/**
 * @author philipp
 *
 */
public class CTMCAnalyzeJobListener implements IJobChangeListener {

	private final IAnalysisListener listener;

	public CTMCAnalyzeJobListener(final IAnalysisListener listener) {
		this.listener = listener;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.runtime.jobs.IJobChangeListener#aboutToRun(org.eclipse
	 * .core.runtime.jobs.IJobChangeEvent)
	 */
	@Override
	public void aboutToRun(final IJobChangeEvent event) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.runtime.jobs.IJobChangeListener#awake(org.eclipse.core
	 * .runtime.jobs.IJobChangeEvent)
	 */
	@Override
	public void awake(final IJobChangeEvent event) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.runtime.jobs.IJobChangeListener#done(org.eclipse.core
	 * .runtime.jobs.IJobChangeEvent)
	 */
	@Override
	public void done(final IJobChangeEvent event) {
		if (!(event.getJob() instanceof CTMCAnalyzeJob)) {
			return;
		}
		if (!event.getResult().isOK()) {
			// no error handling yet.
		}
		CTMCAnalyzeJob job = (CTMCAnalyzeJob) event.getJob();

		Resource resource = job.getModel();

		IFile modelfile = ResourcesPlugin.getWorkspace().getRoot()
				.getFile(new Path(resource.getURI().toPlatformString(true)));

		IFile resultfile = ResourcesPlugin
				.getWorkspace()
				.getRoot()
				.getFile(
						modelfile.getFullPath()
								.addFileExtension("analysis.csv"));

		try {
			PipedInputStream in = new PipedInputStream();
			PipedOutputStream out = new PipedOutputStream(in);

			// When performing a verification, reachability and probability are
			// evaluated. When performing a simulation, only reachability is
			// evaluated.
			if (job.getAnalysis().size() > 0) {
				if (job.getAnalysis().entrySet().iterator().next().getValue().residence == null) {
					out.write("State,Reachability\n".getBytes());
				} else {
					out.write("State,Reachability,Probability\n".getBytes());
				}

				for (Entry<Object, Result> entry : job.getAnalysis().entrySet()) {
					String key = "";
					if (entry.getKey() instanceof State) {
						key = ((State) entry.getKey()).getName();
					} else if (entry.getKey() instanceof String) {
						key = "Label: " + (String) entry.getKey();
					}
					out.write(key.getBytes());
					out.write(',');
					out.write(String.valueOf(entry.getValue().reach).getBytes());
					if (entry.getValue().residence != null) {
						out.write(',');
						out.write(String.valueOf(entry.getValue().residence)
								.getBytes());
					}
					out.write('\n');
				}
			}

			out.close();

			if (resultfile.exists()) {
				resultfile.setContents(in, true, false, null);
			} else {
				resultfile.create(in, true, null);
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block				
			e.printStackTrace();
		} catch (CoreException e) {
			JOptionPane.showMessageDialog(null,
					"Error saving result to csv. Is result csv file from previous run still open?",
					"Error saving result", JOptionPane.ERROR_MESSAGE);
		}

		if (this.listener != null) {
			this.listener.finished(resource, resultfile);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.runtime.jobs.IJobChangeListener#running(org.eclipse.
	 * core.runtime.jobs.IJobChangeEvent)
	 */
	@Override
	public void running(final IJobChangeEvent event) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.runtime.jobs.IJobChangeListener#scheduled(org.eclipse
	 * .core.runtime.jobs.IJobChangeEvent)
	 */
	@Override
	public void scheduled(final IJobChangeEvent event) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.runtime.jobs.IJobChangeListener#sleeping(org.eclipse
	 * .core.runtime.jobs.IJobChangeEvent)
	 */
	@Override
	public void sleeping(final IJobChangeEvent event) {
		// TODO Auto-generated method stub

	}

}