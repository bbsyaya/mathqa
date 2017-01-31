package dekauliya.fyp.mathqa.UI.TopicList;

import android.animation.Animator;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import dekauliya.fyp.mathqa.Models.Concept;
import dekauliya.fyp.mathqa.R;
import dekauliya.fyp.mathqa.Utils.GraphicUtils;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.helpers.AnimatorHelper;
import eu.davidea.flexibleadapter.items.AbstractSectionableItem;
import eu.davidea.viewholders.FlexibleViewHolder;

/**
 * Created by dekauliya on 30/1/17.
 */

public class ConceptItem extends AbstractSectionableItem<ConceptItem.ConceptItemViewHolder,
        TopicHeaderItem> {
    private Concept concept;

    public ConceptItem(Concept concept, TopicHeaderItem header) {
        super(header);
        this.concept = concept;
    }

    public Concept getConcept() {
        return concept;
    }

    public void setConcept(Concept concept) {
        this.concept = concept;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.recycler_concept_item;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ConceptItem){
            ConceptItem item = (ConceptItem) o;
            return this.concept.equals(item.getConcept());
        }
        return false;
    }

    @Override
    public ConceptItemViewHolder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {
        return new ConceptItemViewHolder(inflater.inflate(getLayoutRes(), parent, false), adapter);
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, ConceptItemViewHolder holder, int position, List payloads) {
        holder.mConceptTitle.setText(concept.getName());
    }

    static class ConceptItemViewHolder extends FlexibleViewHolder{
        TextView mConceptTitle;

        public ConceptItemViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            mConceptTitle = (TextView) view.findViewById(R.id.concept_title);
        }

        @Override
        public float getActivationElevation() {
            return GraphicUtils.dpToPx(itemView.getContext(), 4f);
        }

        @Override
        public void scrollAnimators(@NonNull List<Animator> animators, int position, boolean isForward) {
            AnimatorHelper.scaleAnimator(animators, itemView, 0f);
        }
    }

    @Override
    public String toString() {
        return "SubItem[" + super.toString() + "]";
    }
}