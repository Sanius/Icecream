import Link from 'next/link'
import Image from 'next/image'
import { type FC } from 'react'

import { Button } from '@/components/ui/button'
import { ROUTES } from '@/lib/constants'
import { ProductExtended } from '@/types'
import { ProductCard } from '@/app/_components/item-card'

type Props = {
  data: ProductExtended[]
  title?: string
  limit?: number
}

export const ProductList: FC<Props> = ({ data, title, limit }) => {
  const limitedData = limit ? data.slice(0, limit) : data
  return (
    <div className="my-10">
      <h2 className="h2-bold mb-4">{title}</h2>
      {data.length > 0 ? (
        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4">
          {limitedData.map((item: ProductExtended) => (
            <ProductCard data={item} key={item.id} />
          ))}
        </div>
      ) : (
        <div>
          <p>No products found</p>
        </div>
      )}
    </div>
  )
}

export const ViewAllProductsButton = () => {
  return (
    <div className="flex justify-center items-center my-8">
      <Button asChild className="px-8 py-4 text-lg font-semibold">
        <Link href="/search">View All Products</Link>
      </Button>
    </div>
  )
}
