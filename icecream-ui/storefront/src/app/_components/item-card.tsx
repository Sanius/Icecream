import Image from 'next/image'
import Link from 'next/link'
import { type FC } from 'react'
import { HiMagnifyingGlassPlus } from 'react-icons/hi2'
import { IoHeartOutline } from 'react-icons/io5'
import { FaRegStar } from 'react-icons/fa'

import { ROUTES } from '@/lib/constants'
import { cn, makeStorageUrl } from '@/lib/utils'
import type { CategoryExtended, ProductExtended } from '@/types'
import { Card, CardContent, CardHeader } from '@/components/ui/card'

type CategoryCardProps = {
  data: CategoryExtended
}

export const CategoryCard: FC<CategoryCardProps> = ({ data }) => {
  const { avatar, name, slug } = data
  return (
    <div className="relative rounded-sm overflow-hidden group">
      <div className="w-52 h-72">
        <Image
          src={makeStorageUrl(avatar?.relativePath)}
          alt={avatar?.description}
          fill
          className="object-cover"
        />
      </div>
      <Link
        href="/"
        className="absolute inset-0 bg-black bg-opacity-40 flex items-center justify-center text-xl text-white font-roboto font-medium group-hover:bg-opacity-60 transition"
      >
        {name}
      </Link>
    </div>
  )
}

type ProductCardProps = {
  data: ProductExtended
}

export const ProductCard: FC<ProductCardProps> = ({ data }) => {
  const { media, id, name, price, slug, stockQuantity } = data
  return (
    <Card className="w-full max-w-sm">
      <CardHeader className="p-0 items-center">
        <Link href={`/product/${slug}`}>
          <Image src={makeStorageUrl(media[0]?.relativePath)} alt={name} height={300} width={300} priority={true} />
        </Link>
      </CardHeader>
      <CardContent className="p-4 grid gap-4">
        <Link href={`/product/${slug}`}>
          <h2 className="text-sm font-medium">{name}</h2>
        </Link>
        <div className="flex-between gap-4">
          {stockQuantity > 0 ? (
            <ProductPrice value={Number(price)} />
          ) : (
            <p className="text-destructive">Out Of Stock</p>
          )}
        </div>
      </CardContent>
    </Card>
  )
}

type ProductPriceProps = {
  value: number
  className?: string
}

const ProductPrice: FC<ProductPriceProps> = ({ value, className }) => {
  const stringValue = value.toFixed(2)
  const [intValue, floatValue] = stringValue.split('.')
  return (
    <p className={cn('text-2xl', className)}>
      <span className="text-xs align-super">$</span>
      {intValue}
      <span className="text-xs align-super"></span>
    </p>
  )
}
