"""0 Create customer db

Revision ID: 66ec40ed223b
Revises: 
Create Date: 2023-05-18 11:21:09.286865

"""
from alembic import op
import sqlalchemy as sa


# revision identifiers, used by Alembic.
revision = '66ec40ed223b'
down_revision = None
branch_labels = None
depends_on = None


def upgrade() -> None:
    # ### commands auto generated by Alembic - please adjust! ###
    op.create_table('customer',
    sa.Column('id', sa.BIGINT(), nullable=False),
    sa.Column('email', sa.VARCHAR(length=100), nullable=True),
    sa.Column('phone', sa.VARCHAR(length=100), nullable=True),
    sa.Column('last_name', sa.VARCHAR(length=100), nullable=True),
    sa.Column('first_name', sa.VARCHAR(length=100), nullable=True),
    sa.Column('is_active', sa.BOOLEAN(), nullable=True),
    sa.Column('user_id', sa.VARCHAR(length=500), nullable=True),
    sa.PrimaryKeyConstraint('id')
    )
    op.create_table('customer_address',
    sa.Column('id', sa.BIGINT(), autoincrement=True, nullable=False),
    sa.Column('address_id', sa.BIGINT(), nullable=True),
    sa.PrimaryKeyConstraint('id')
    )
    # ### end Alembic commands ###


def downgrade() -> None:
    # ### commands auto generated by Alembic - please adjust! ###
    op.drop_table('customer_address')
    op.drop_table('customer')
    # ### end Alembic commands ###